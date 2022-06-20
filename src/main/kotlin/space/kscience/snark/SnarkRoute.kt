package space.kscience.snark

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.file
import io.ktor.server.http.content.files
import io.ktor.server.http.content.static
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import kotlinx.html.HTML
import java.nio.file.Path

/**
 * An abstraction, which is used to render sites to the different rendering engines
 */
interface SnarkRoute {

    fun staticFile(remotePath: String, file: Path)

    fun staticDirectory(remotePath: String, directory: Path)

    context(PageContext) fun page(route: String = "", content: context(PageContext, HTML) () -> Unit)

    context(PageContext) fun route(route: String, block: context(PageContext, SnarkRoute) () -> Unit)
}

class KtorRouteBuilder(private val ktorRoute: Route) : SnarkRoute {
    override fun staticFile(remotePath: String, file: Path) {
        ktorRoute.file(remotePath, file.toFile())
    }

    override fun staticDirectory(remotePath: String, directory: Path) {
        ktorRoute.static(remotePath) {
            files(directory.toFile())
        }
    }

    context(PageContext) override fun page(route: String, content: context(PageContext, HTML)() -> Unit) {
        ktorRoute.get(route) {
            withRequest(call.request) {
                call.respondHtml {
                    content(this@PageContext, this)
                }
            }
        }
    }

    context(PageContext) override fun route(
        route: String,
        block: context(PageContext, SnarkRoute)() -> Unit,
    ) {
        ktorRoute.route(route) {
            block(this@PageContext, KtorRouteBuilder(this))
        }
    }
}

inline fun Route.snark(
    pageContext: PageContext,
    block: context(PageContext, SnarkRoute)() -> Unit,
) {
    block(pageContext, KtorRouteBuilder(this@snark))
}