package space.kscience.snark

import kotlinx.html.HTML
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.context.ContextAware
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.meta.Laminate
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.parseAsName
import java.nio.file.Path

internal fun Name.toWebPath() = tokens.joinToString(separator = "/")

/**
 * An abstraction, which is used to render sites to the different rendering engines
 */
interface SiteBuilder : ContextAware {

    val data: SiteData

    override val context: Context get() = data.context

    fun assetFile(remotePath: String, file: Path)

    fun assetDirectory(remotePath: String, directory: Path)

    fun assetResourceFile(remotePath: String, resourcesPath: String)

    fun assetResourceDirectory(resourcesPath: String)

    fun page(route: Name = Name.EMPTY, content: context(SiteData, HTML) () -> Unit)

    /**
     * Create a new branch builder with replaced [data]
     */
    fun withData(newData: SiteData): SiteBuilder

    /**
     * Create a route
     */
    fun route(subRoute: Name): SiteBuilder
}

val SiteBuilder.snark get() = data.snark

public inline fun SiteBuilder.route(route: Name, block: SiteBuilder.() -> Unit) {
    route(route).apply(block)
}

public inline fun SiteBuilder.route(route: String, block: SiteBuilder.() -> Unit) {
    route(route.parseAsName()).apply(block)
}

/**
 * Create a stand-alone site at a given node
 */
public fun SiteBuilder.mountSite(route: Name, dataRoot: DataTree<*>, block: SiteBuilder.() -> Unit) {
    val mountedData = data.copy(
        data = dataRoot,
        baseUrlPath = data.resolveRef(route.tokens.joinToString(separator = "/")),
        meta = Laminate(dataRoot.meta, data.meta) //layering dataRoot meta over existing data
    )
    route(route) {
        withData(mountedData).block()
    }
}
