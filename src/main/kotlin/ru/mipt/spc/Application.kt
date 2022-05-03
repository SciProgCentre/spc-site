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
import java.nio.file.*
import kotlin.io.path.createDirectories
import kotlin.io.path.isRegularFile
import kotlin.io.path.relativeTo

fun CommonAttributeGroupFacade.css(block: CssBuilder.() -> Unit) {
    style = CssBuilder().block().toString()
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

private fun useResource(uri: URI, block: (Path) -> Unit) {
    try {
        block(Paths.get(uri))
    } catch (ex: FileSystemNotFoundException) {
        //Copy everything into a temporary directory
        FileSystems.newFileSystem(uri, emptyMap<String, Any>()).use { fs ->
            val rootPath: Path = fs.provider().getPath(uri)
            val tmpDirectory = Files.createTempDirectory("snark")
            Files.walk(rootPath).forEach { source: Path ->
                if (source.isRegularFile()) {
                    val relative = source.relativeTo(rootPath).toString()
                    val destination: Path = tmpDirectory.resolve(relative)
                    destination.parent.createDirectories()
                    Files.copy(source, destination)
                }
            }
            block(tmpDirectory)
        }
    }
}


fun main() {
    val context = Context("spc-site") {
        plugin(SnarkPlugin)
    }
    embeddedServer(Netty, port = 7080, watchPaths = listOf("classes", "resources")) {
        install(StatusPages) {
            exception<AuthenticationException> { call, _ ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { call, _ ->
                call.respond(HttpStatusCode.Forbidden)
            }
        }
        useResource(javaClass.getResource("/magprog")!!.toURI()) { path ->
            magProgPage(context, rootPath = path)
        }
    }.start(wait = true)

}
