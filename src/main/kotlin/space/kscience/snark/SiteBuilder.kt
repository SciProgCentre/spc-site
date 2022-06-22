package space.kscience.snark

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.*
import io.ktor.server.routing.Route
import io.ktor.server.routing.createRouteFromPath
import io.ktor.server.routing.get
import kotlinx.html.HTML
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.context.ContextAware
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.parseAsName
import java.nio.file.Path

internal fun Name.toWebPath() = tokens.joinToString(separator = "/")
/**
 * An abstraction, which is used to render sites to the different rendering engines
 */
interface SiteBuilder : ContextAware {

    val data: SiteData

    override val context: Context get() = data.context

    fun assetFile(remotePath: String, file: Path)

    fun assetDirectory(remotePath: String, directory: Path)

    fun assetResourceFile(remotePath: String, resourcesPath: String)

    fun assetResourceDirectory(resourcesPath: String)

    fun page(route: Name = Name.EMPTY, content: context(SiteData, HTML) () -> Unit)

    /**
     * Create a route
     */
    fun route(subRoute: Name): SiteBuilder
}

public inline fun SiteBuilder.route(route: Name, block: SiteBuilder.() -> Unit) {
    route(route).apply(block)
}

public inline fun SiteBuilder.route(route: String, block: SiteBuilder.() -> Unit) {
    route(route.parseAsName()).apply(block)
}

class KtorSiteRoute(override val data: SiteData, private val ktorRoute: Route) : SiteBuilder {
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
                content(data.copyWithRequestHost(call.request), this)
            }
        }
    }

    override fun route(subRoute: Name): SiteBuilder =
        KtorSiteRoute(data, ktorRoute.createRouteFromPath(subRoute.toWebPath()))

    override fun assetResourceFile(remotePath: String, resourcesPath: String) {
        ktorRoute.resource(resourcesPath, resourcesPath)
    }

    override fun assetResourceDirectory(resourcesPath: String) {
        ktorRoute.resources(resourcesPath)
    }
}

inline fun Route.snarkSite(
    siteContext: SiteData,
    block: context(SiteData, SiteBuilder)() -> Unit,
) {
    block(siteContext, KtorSiteRoute(siteContext, this@snarkSite))
}