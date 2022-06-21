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
import java.nio.file.Path

/**
 * An abstraction, which is used to render sites to the different rendering engines
 */
interface SiteBuilder : ContextAware {

    val siteContext: SiteContext

    override val context: Context get() = siteContext.context

    fun staticFile(remotePath: String, file: Path)

    fun staticDirectory(remotePath: String, directory: Path)

    fun staticResourceFile(remotePath: String, resourcesPath: String)

    fun staticResourceDirectory(resourcesPath: String)

    fun page(route: String = "", content: context(SiteContext, HTML) () -> Unit)

    /**
     * Create a route
     */
    fun route(subRoute: String): SiteBuilder
}

public inline fun SiteBuilder.route(route: String, block: SiteBuilder.() -> Unit) {
    route(route).apply(block)
}

class KtorSiteRoute(override val siteContext: SiteContext, private val ktorRoute: Route) : SiteBuilder {
    override fun staticFile(remotePath: String, file: Path) {
        ktorRoute.file(remotePath, file.toFile())
    }

    override fun staticDirectory(remotePath: String, directory: Path) {
        ktorRoute.static(remotePath) {
            files(directory.toFile())
        }
    }

    override fun page(route: String, content: context(SiteContext, HTML)() -> Unit) {
        ktorRoute.get(route) {
            call.respondHtml {
                content(siteContext.copyWithRequestHost(call.request), this)
            }
        }
    }

    override fun route(subRoute: String): SiteBuilder =
        KtorSiteRoute(siteContext, ktorRoute.createRouteFromPath(subRoute))

    override fun staticResourceFile(remotePath: String, resourcesPath: String) {
        ktorRoute.resource(resourcesPath, resourcesPath)
    }

    override fun staticResourceDirectory(resourcesPath: String) {
        ktorRoute.resources(resourcesPath)
    }
}

inline fun Route.snarkSite(
    siteContext: SiteContext,
    block: context(SiteContext, SiteBuilder)() -> Unit,
) {
    block(siteContext, KtorSiteRoute(siteContext, this@snarkSite))
}