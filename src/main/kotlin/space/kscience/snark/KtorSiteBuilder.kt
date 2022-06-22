package space.kscience.snark

import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.*
import io.ktor.server.plugins.origin
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.host
import io.ktor.server.request.port
import io.ktor.server.routing.Route
import io.ktor.server.routing.createRouteFromPath
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.HTML
import space.kscience.dataforge.names.Name
import java.nio.file.Path

class KtorSiteBuilder(override val data: SiteData, private val ktorRoute: Route) : SiteBuilder {

    override fun assetFile(remotePath: String, file: Path) {
        ktorRoute.file(remotePath, file.toFile())
    }

    override fun assetDirectory(remotePath: String, directory: Path) {
        ktorRoute.static(remotePath) {
            files(directory.toFile())
        }
    }

    override fun page(route: Name, content: context(SiteData, HTML)() -> Unit) {
        ktorRoute.get(route.toWebPath()) {
            call.respondHtml {
                val dataWithUrl = data.copyWithRequestHost(call.request)
                content(dataWithUrl, this)
            }
        }
    }

    override fun route(subRoute: Name): SiteBuilder =
        KtorSiteBuilder(data, ktorRoute.createRouteFromPath(subRoute.toWebPath()))

    override fun assetResourceFile(remotePath: String, resourcesPath: String) {
        ktorRoute.resource(resourcesPath, resourcesPath)
    }

    override fun assetResourceDirectory(resourcesPath: String) {
        ktorRoute.resources(resourcesPath)
    }

    override fun withData(newData: SiteData): SiteBuilder = KtorSiteBuilder(newData, ktorRoute)
}

@PublishedApi
internal fun SiteData.copyWithRequestHost(request: ApplicationRequest): SiteData {
    val uri = URLBuilder(
        protocol = URLProtocol.createOrDefault(request.origin.scheme),
        host = request.host(),
        port = request.port(),
        pathSegments = baseUrlPath.split("/"),
    )
    return copy(baseUrlPath = uri.buildString())
}

inline fun Route.snarkSite(
    data: SiteData,
    block: SiteBuilder.() -> Unit,
) {
    block(KtorSiteBuilder(data, this@snarkSite))
}

fun Application.snarkSite(
    data: SiteData,
    block: SiteBuilder.() -> Unit,
) {
    routing {
        snarkSite(data, block)
    }
}

context (Application) fun SnarkPlugin.site(
    block: SiteBuilder.() -> Unit,
) {
    snarkSite(SiteData.empty(this), block)
}