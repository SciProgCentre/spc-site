package center.sciprog

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.forwardedheaders.ForwardedHeaders
import io.ktor.server.plugins.forwardedheaders.XForwardedHeaders
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import space.kscience.dataforge.context.Global
import space.kscience.dataforge.context.request
import space.kscience.dataforge.data.DataSet
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.data.node
import space.kscience.dataforge.data.populateFrom
import space.kscience.dataforge.misc.DFExperimental
import space.kscience.snark.html.SiteBuilder
import space.kscience.snark.html.SnarkHtmlPlugin
import space.kscience.snark.html.readDirectory
import space.kscience.snark.ktor.prepareSnarkDataCacheDirectory
import space.kscience.snark.ktor.site
import java.nio.file.Path


@Suppress("unused")
fun Application.spcModule() {
//    install(HttpsRedirect)
    install(ForwardedHeaders)
    install(XForwardedHeaders)

    val dataPath = Path.of("data")

    prepareSnarkDataCacheDirectory(dataPath)

    val snark = Global.request(SnarkHtmlPlugin)
    val siteData = snark.readDirectory(dataPath)

    site(snark, siteData, block = SiteBuilder::spcSite)

    routing {
        get("magprog") {
            call.respondRedirect("education/masters")
        }
    }
}


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

