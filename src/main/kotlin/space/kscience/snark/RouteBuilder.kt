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

interface RouteBuilder {
    val pageContext: PageContext

    fun staticFile(remotePath: String, file: Path)

    fun staticDirectory(remotePath: String, directory: Path)

    fun page(route: String = "", content: context(PageContext) HTML.() -> Unit)

    fun route(route: String, block: RouteBuilder.() -> Unit)
}

class KtorRouteBuilder(override val pageContext: PageContext, private val ktorRoute: Route) : RouteBuilder {
    override fun staticFile(remotePath: String, file: Path) {
        ktorRoute.file(remotePath, file.toFile())
    }

    override fun staticDirectory(remotePath: String, directory: Path) {
        ktorRoute.static(remotePath) {
            files(directory.toFile())
        }
    }

    override fun page(route: String, content: context(PageContext) HTML.() -> Unit) {
        ktorRoute.get(route) {
            with(pageContext) {
                withRequest(call.request) {
                    val innerContext = this
                    call.respondHtml {
                        content(innerContext, this)
                    }
                }
            }
        }
    }

    override fun route(route: String, block: RouteBuilder.() -> Unit) {
        ktorRoute.route(route) {
            block(KtorRouteBuilder(pageContext, this))
        }
    }
}

fun Route.snark(pageContext: PageContext, block: context(PageContext) RouteBuilder.() -> Unit) {
    with(pageContext){
        block(KtorRouteBuilder(pageContext, this@snark))
    }
}