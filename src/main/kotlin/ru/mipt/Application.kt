package ru.mipt

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import ru.mipt.plugins.configureTemplating
import ru.mipt.spc.magprog.DataSetSiteContext
import ru.mipt.spc.magprog.DirectoryDataTree
import ru.mipt.spc.magprog.SiteContext
import ru.mipt.spc.magprog.magProgPage
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.io.io
import space.kscience.dataforge.io.yaml.YamlPlugin
import java.nio.file.Path


class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

internal fun Application.magProgSite(prefix: String = "magprog"){
    val context = Context("spc-site"){
        plugin(YamlPlugin)
    }

    val io = context.io
    val directory = javaClass.getResource("/magprog/content")!!.toURI()
    val content = DirectoryDataTree(io, Path.of(directory))


    val magprogSiteContext: SiteContext = DataSetSiteContext(context,"magprog", content)

    routing {
        route(prefix){
            get {
                call.respondHtml {
                    with(magprogSiteContext){
                        magProgPage()
                    }
                }
            }
            static {
                resources("magprog/assets")
            }
        }
    }
}


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        magProgSite()
        install(StatusPages) {
            exception<AuthenticationException> { call, cause ->
                call.respond(HttpStatusCode.Unauthorized)
            }
            exception<AuthorizationException> { call, cause ->
                call.respond(HttpStatusCode.Forbidden)
            }
        }
        configureTemplating()
    }.start(wait = true)
}
