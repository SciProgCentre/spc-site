package ru.mipt.spc

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.forwardedheaders.ForwardedHeaders
import space.kscience.snark.SnarkEnvironment
import space.kscience.snark.ktor.extractResources
import space.kscience.snark.ktor.prepareSnarkDataCacheDirectory
import space.kscience.snark.ktor.site
import java.nio.file.Path
import kotlin.io.path.div

@Suppress("unused")
fun Application.spcModule() {
//    install(HttpsRedirect)
    install(ForwardedHeaders)

    val dataPath = Path.of("data")

    prepareSnarkDataCacheDirectory(dataPath)

    SnarkEnvironment.default.site {

        extractResources(
            "/common",
            dataPath / "common"
        )

        val homeDataPath = extractResources(
            "/home",
            dataPath / "home"
        )

        spcHome(dataPath = homeDataPath)

        val mastersDataPath = extractResources(
            "/magprog",
            dataPath / "magprog"
        )

        spcMasters(dataPath = mastersDataPath)
    }
}


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

