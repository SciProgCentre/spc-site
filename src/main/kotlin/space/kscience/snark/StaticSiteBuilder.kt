package space.kscience.snark

import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.stream.createHTML
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.isEmpty
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.copyTo
import kotlin.io.path.createDirectories
import kotlin.io.path.relativeTo
import kotlin.io.path.writeText


class StaticSiteBuilder(override val data: SiteData, private val path: Path) : SiteBuilder {
    private fun Path.copyRecursively(target: Path) {
        Files.walk(this).forEach { source: Path ->
            val destination: Path = target.resolve(source.relativeTo(this))
            source.copyTo(destination,true)
        }
    }

    override fun assetFile(remotePath: String, file: Path) {
        val targetPath = path.resolve(remotePath)
        targetPath.parent.createDirectories()
        file.copyTo(targetPath, true)
    }

    override fun assetDirectory(remotePath: String, directory: Path) {
        val targetPath = path.resolve(remotePath)
        targetPath.parent.createDirectories()
        directory.copyRecursively(targetPath)
    }

    override fun assetResourceFile(remotePath: String, resourcesPath: String) {
        val targetPath = path.resolve(remotePath)
        targetPath.parent.createDirectories()
        javaClass.getResource(resourcesPath)?.let { Path.of(it.toURI()) }?.copyTo(targetPath,true)
    }

    override fun assetResourceDirectory(resourcesPath: String) {
        path.parent.createDirectories()
        javaClass.getResource(resourcesPath)?.let { Path.of(it.toURI()) }?.copyRecursively(path)
    }

    override fun page(route: Name, content: context(SiteData, HTML) () -> Unit) {
        val htmlBuilder = createHTML()

        htmlBuilder.html {
            content(data, this)
        }

        val newPath = if (route.isEmpty()) {
            path.resolve("index.html")
        } else {
            path.resolve(route.toWebPath() + ".html")
        }

        newPath.parent.createDirectories()
        newPath.writeText(htmlBuilder.finalize())
    }

    override fun route(subRoute: Name): SiteBuilder = StaticSiteBuilder(data, path.resolve(subRoute.toWebPath()))

    override fun withData(newData: SiteData): SiteBuilder = StaticSiteBuilder(newData, path)

}

fun SnarkPlugin.static(path: Path, block: SiteBuilder.() -> Unit) {
    StaticSiteBuilder(SiteData.empty(this), path).block()
}