package ru.mipt.spc

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kotlinx.css.CssBuilder
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.style
import ru.mipt.spc.magprog.magProgPage
import space.kscience.dataforge.context.Context
import space.kscience.snark.SnarkPlugin
import java.net.URI
import java.nio.file.FileSystemNotFoundException
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.Paths

fun CommonAttributeGroupFacade.css(block: CssBuilder.() -> Unit) {
    style = CssBuilder().block().toString()
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

private fun useResource(uri: URI, block: (Path) -> Unit) {
    try {
        block(Paths.get(uri))
    } catch (ex: FileSystemNotFoundException) {
        FileSystems.newFileSystem(uri, emptyMap<String, Any>()).use { fs ->
            val p: Path = fs.provider().getPath(uri)
            block(p)
        }
    }
}

fun main() {
    val context = Context("spc-site") {
        plugin(SnarkPlugin)
    }
    embeddedServer(Netty, port = 8080, watchPaths = listOf("classes", "resources")) {
        useResource(javaClass.getResource("/magprog")!!.toURI()) {
            install(StatusPages) {
                exception<AuthenticationException> { call, _ ->
                    call.respond(HttpStatusCode.Unauthorized)
                }
                exception<AuthorizationException> { call, _ ->
                    call.respond(HttpStatusCode.Forbidden)
                }
            }
            magProgPage(context, rootPath = it)
        }
    }.start(wait = true)

}
