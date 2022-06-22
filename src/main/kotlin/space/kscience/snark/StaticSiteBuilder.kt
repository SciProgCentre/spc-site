package space.kscience.snark

import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.stream.createHTML
import space.kscience.dataforge.names.Name
import java.nio.file.Path
import kotlin.io.path.copyTo
import kotlin.io.path.createDirectories
import kotlin.io.path.writeText

class StaticSiteBuilder(override val data: SiteData, private val path: Path) : SiteBuilder {
    override fun assetFile(remotePath: String, file: Path) {
        file.copyTo(path.resolve(remotePath))
    }

    override fun assetDirectory(remotePath: String, directory: Path) {
        directory.copyTo(path.resolve(remotePath))
    }

    override fun assetResourceFile(remotePath: String, resourcesPath: String) {
        javaClass.getResource(resourcesPath)?.let { Path.of(it.toURI()) }?.copyTo(path.resolve(remotePath))
    }

    override fun assetResourceDirectory(resourcesPath: String) {
        javaClass.getResource(resourcesPath)?.let { Path.of(it.toURI()) }?.copyTo(path)
    }

    override fun page(route: Name, content: context(SiteData, HTML) () -> Unit) {
        val htmlBuilder = createHTML()

        htmlBuilder.html {
            content(data, this)
        }

        val newPath = path.resolve(route.toWebPath() + ".html")

        newPath.parent.createDirectories()
        newPath.writeText(htmlBuilder.finalize())
    }

    override fun route(subRoute: Name): SiteBuilder = StaticSiteBuilder(data, path.resolve(subRoute.toWebPath()))

    override fun withData(newData: SiteData): SiteBuilder = StaticSiteBuilder(newData, path)

}