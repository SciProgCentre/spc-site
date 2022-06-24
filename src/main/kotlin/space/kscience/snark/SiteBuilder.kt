package space.kscience.snark

import kotlinx.html.HTML
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.context.ContextAware
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.data.DataTreeItem
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.NameToken
import space.kscience.dataforge.names.parseAsName
import java.nio.file.Path
import kotlin.reflect.KType
import kotlin.reflect.typeOf


/**
 * An abstraction, which is used to render sites to the different rendering engines
 */
interface SiteBuilder : ContextAware {

    val data: DataTree<*>

    val snark: SnarkPlugin

    override val context: Context get() = snark.context

    val meta: Meta

    fun assetFile(remotePath: String, file: Path)

    fun assetDirectory(remotePath: String, directory: Path)

    fun assetResourceFile(remotePath: String, resourcesPath: String)

    fun assetResourceDirectory(resourcesPath: String)

    fun page(route: Name = Name.EMPTY, content: context(PageBuilder, HTML) () -> Unit)

    /**
     * Create a route with optional data tree override. For example one could use a subtree of the initial tree.
     * By default, the same data tree is used for route
     */
    fun route(
        routeName: Name,
        dataOverride: DataTree<*>? = null,
        metaOverride: Meta? = null,
        setAsRoot: Boolean = false,
    ): SiteBuilder

    companion object {
        val INDEX_PAGE_TOKEN: NameToken = NameToken("index")
    }
}

public inline fun SiteBuilder.route(
    route: Name,
    dataOverride: DataTree<*>? = null,
    metaOverride: Meta? = null,
    setAsRoot: Boolean = false,
    block: SiteBuilder.() -> Unit,
) {
    route(route, dataOverride, metaOverride, setAsRoot).apply(block)
}

public inline fun SiteBuilder.route(
    route: String,
    dataOverride: DataTree<*>? = null,
    metaOverride: Meta? = null,
    setAsRoot: Boolean = false,
    block: SiteBuilder.() -> Unit,
) {
    route(route.parseAsName(), dataOverride, metaOverride, setAsRoot).apply(block)
}


///**
// * Create a stand-alone site at a given node
// */
//public fun SiteBuilder.site(route: Name, dataRoot: DataTree<*>, block: SiteBuilder.() -> Unit) {
//    val mountedData = data.copy(
//        data = dataRoot,
//        baseUrlPath = data.resolveRef(route.tokens.joinToString(separator = "/")),
//        meta = Laminate(dataRoot.meta, data.meta) //layering dataRoot meta over existing data
//    )
//    route(route) {
//        withData(mountedData).block()
//    }
//}

//TODO move to DF
fun DataTree.Companion.empty(meta: Meta = Meta.EMPTY) = object : DataTree<Any> {
    override val items: Map<NameToken, DataTreeItem<Any>> get() = emptyMap()
    override val dataType: KType get() = typeOf<Any>()
    override val meta: Meta get() = meta
}