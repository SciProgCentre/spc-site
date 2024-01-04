package center.sciprog

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.config.tryGetString
import io.ktor.server.plugins.forwardedheaders.ForwardedHeaders
import io.ktor.server.plugins.forwardedheaders.XForwardedHeaders
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.context.request
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.workspace.readDataDirectory
import space.kscience.snark.html.*
import space.kscience.snark.ktor.prepareSnarkDataCacheDirectory
import space.kscience.snark.ktor.site
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.isRegularFile
import kotlin.io.path.relativeTo
import kotlin.io.path.toPath


private fun Application.copyResource(resource: String, dataDirectory: Path) {
    val uri = javaClass.getResource("/$resource")?.toURI()
        ?: error("Resource $resource not found")
    val targetPath = dataDirectory.resolve(resource)

    if (Files.isDirectory(targetPath)) {
        log.info("Using existing data directory at $targetPath.")
    } else {
        log.info("Copying data from $uri into $targetPath.")
        targetPath.createDirectories()
        //Copy everything into a temporary directory

        fun copyFromPath(rootPath: Path) {
            Files.walk(rootPath).forEach { source: Path ->
                if (source.isRegularFile()) {
                    val relative = source.relativeTo(rootPath).toString()
                    val destination: Path = targetPath.resolve(relative)
                    destination.parent.createDirectories()
                    Files.copy(source, destination)
                }
            }
        }

        if ("jar" == uri.scheme) {
            FileSystems.newFileSystem(uri, emptyMap<String, Any>()).use { fs ->
                val rootPath: Path = fs.provider().getPath(uri)
                copyFromPath(rootPath)
            }
        } else {
            val rootPath = uri.toPath()
            copyFromPath(rootPath)
        }
    }
}


@Suppress("unused")
fun Application.spcModule() {
//    install(HttpsRedirect)
    install(ForwardedHeaders)
    install(XForwardedHeaders)

    val context = Context {
        plugin(SnarkHtml)
    }

    val snark = context.request(SnarkHtml)

    val dataDirectory = Path.of(
        environment.config.tryGetString("ktor.environment.dataDirectory") ?: "data"
    )

    if (!prepareSnarkDataCacheDirectory(dataDirectory)) {
        copyResource("common", dataDirectory)
        copyResource("home", dataDirectory)
        copyResource("magprog", dataDirectory)
    }

    val siteData: DataTree<Any> = snark.io.readDataDirectory(dataDirectory)

    routing {
        get("magprog") {
            call.respondRedirect("education/masters")
        }
        site(context, siteData, content = spcSite)
    }
}


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

