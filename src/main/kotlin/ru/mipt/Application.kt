package ru.mipt

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.files
import io.ktor.server.http.content.static
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.css.CssBuilder
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.style
import ru.mipt.plugins.configureTemplating
import ru.mipt.spc.magprog.DataSetPageContext
import ru.mipt.spc.magprog.PageContext
import ru.mipt.spc.magprog.magProgPage
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.io.io
import space.kscience.snark.DirectoryDataTree
import space.kscience.snark.SnarkPlugin
import java.nio.file.Path

fun CommonAttributeGroupFacade.css(block: CssBuilder.() -> Unit) {
    style = CssBuilder().block().toString()
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

internal fun Application.magProgPage(rootPath: Path, prefix: String = "magprog") {
    val context = Context("spc-site") {
        plugin(SnarkPlugin)
    }

    val io = context.io
    val content = DirectoryDataTree(io, rootPath.resolve("content"))


    val magprogPageContext: PageContext = DataSetPageContext(context, prefix, content)

    routing {
        route(prefix) {
            get {
                call.respondHtml {
                    with(magprogPageContext) {
                        magProgPage()
                    }
                }
            }
            static {
                files(rootPath.resolve("assets").toFile())
            }
        }
    }
}


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        magProgPage(rootPath = Path.of(javaClass.getResource("/magprog")!!.toURI()))
        install(StatusPages) {
            exception<AuthenticationException> { call, _ ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { call, _ ->
                call.respond(HttpStatusCode.Forbidden)
            }
        }
        configureTemplating()
    }.start(wait = true)
}
