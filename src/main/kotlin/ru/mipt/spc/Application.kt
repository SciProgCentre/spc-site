package ru.mipt.spc

import io.ktor.server.application.Application
import io.ktor.server.application.log
import kotlinx.css.CssBuilder
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.style
import space.kscience.dataforge.context.Context
import space.kscience.snark.SnarkPlugin
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.div
import kotlin.io.path.isRegularFile
import kotlin.io.path.relativeTo

fun CommonAttributeGroupFacade.css(block: CssBuilder.() -> Unit) {
    style = CssBuilder().block().toString()
}

private fun Application.resolveData(uri: URI, targetPath: Path): Path {
    if (Files.isDirectory(targetPath)) {
        log.info("Using existing data directory at $targetPath.")
    } else {
        log.info("Copying data from $uri into $targetPath.")
        targetPath.createDirectories()
        //Copy everything into a temporary directory
        FileSystems.newFileSystem(uri, emptyMap<String, Any>()).use { fs ->
            val rootPath: Path = fs.provider().getPath(uri)
            Files.walk(rootPath).forEach { source: Path ->
                if (source.isRegularFile()) {
                    val relative = source.relativeTo(rootPath).toString()
                    val destination: Path = targetPath.resolve(relative)
                    destination.parent.createDirectories()
                    Files.copy(source, destination)
                }
            }
        }
    }
    return targetPath
}

@Suppress("unused")
fun Application.spcModule() {
    val context = Context("spc-site") {
        plugin(SnarkPlugin)
    }

    val dataPath = Path.of("data")

    val homeDataPath = resolveData(
        javaClass.getResource("/home")!!.toURI(),
        dataPath / "home"
    )

    spcHome(context, rootPath = homeDataPath)


    val mastersDataPath = resolveData(
        javaClass.getResource("/magprog")!!.toURI(),
        dataPath / "magprog"
    )

    spcMaster(context, dataPath = mastersDataPath)
}


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

