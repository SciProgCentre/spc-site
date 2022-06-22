package space.kscience.snark

import kotlinx.coroutines.runBlocking
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.data.DataTreeItem
import space.kscience.dataforge.data.await
import space.kscience.dataforge.data.getItem
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.getIndexed
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.NameToken
import space.kscience.dataforge.names.asName
import space.kscience.dataforge.names.parseAsName
import space.kscience.snark.SiteLayout.Companion.ASSETS_KEY
import space.kscience.snark.SiteLayout.Companion.INDEX_PAGE_TOKEN
import space.kscience.snark.SiteLayout.Companion.LAYOUT_KEY
import java.nio.file.Path
import kotlin.reflect.typeOf

internal fun SiteBuilder.assetsFrom(rootMeta: Meta) {
    rootMeta.getIndexed("resource".asName()).forEach { (_, meta) ->

        val path by meta.string()
        val remotePath by meta.string()

        path?.let { resourcePath ->
            //If remote path provided, use a single resource
            remotePath?.let {
                assetResourceFile(it, resourcePath)
                return@forEach
            }

            //otherwise use package resources
            assetResourceDirectory(resourcePath)
        }
    }

    rootMeta.getIndexed("file".asName()).forEach { (_, meta) ->
        val remotePath by meta.string { error("File remote path is not provided") }
        val path by meta.string { error("File path is not provided") }
        assetFile(remotePath, Path.of(path))
    }

    rootMeta.getIndexed("directory".asName()).forEach { (_, meta) ->
        val path by meta.string { error("Directory path is not provided") }
        assetDirectory("", Path.of(path))
    }
}

/**
 * Recursively renders the data items in [data]. If [LAYOUT_KEY] is defined in an item, use it to load
 * layout from the context, otherwise render children nodes as name segments and individual data items using [dataRenderer].
 */
fun SiteBuilder.pages(
    data: DataTreeItem<*>,
    dataRenderer: SiteBuilder.(Data<*>) -> Unit = SiteLayout.defaultDataRenderer,
) {
    val layoutMeta = data.meta[LAYOUT_KEY]
    if (layoutMeta != null) {
        //use layout if it is defined
        this.data.snark.layout(layoutMeta).render(data)
    } else {
        when (data) {
            is DataTreeItem.Node -> {
                data.tree.items.forEach { (token, item) ->
                    //Don't apply index token
                    if (token == INDEX_PAGE_TOKEN) {
                        pages(item, dataRenderer)
                    }
                    route(token.toString()) {
                        pages(item, dataRenderer)
                    }
                }
            }
            is DataTreeItem.Leaf -> {
                dataRenderer.invoke(this, data.data)
            }
        }
        data.meta[ASSETS_KEY]?.let {
            assetsFrom(it)
        }
    }
    //TODO watch for changes
}

/**
 * Render all pages in a node with given name
 */
fun SiteBuilder.pages(
    dataPath: Name,
    remotePath: Name = dataPath,
    dataRenderer: SiteBuilder.(Data<*>) -> Unit = SiteLayout.defaultDataRenderer,
) {
    val item = data.getItem(dataPath) ?: error("No data found by name $dataPath")
    route(remotePath) {
        pages(item, dataRenderer)
    }
}

fun SiteBuilder.pages(
    dataPath: String,
    remotePath: Name = dataPath.parseAsName(),
    dataRenderer: SiteBuilder.(Data<*>) -> Unit = SiteLayout.defaultDataRenderer,
) {
    pages(dataPath.parseAsName(), remotePath, dataRenderer = dataRenderer)
}


fun interface SiteLayout {

    context(SiteBuilder) fun render(item: DataTreeItem<*>)

    companion object {
        const val LAYOUT_KEY = "layout"
        const val ASSETS_KEY = "assets"
        val INDEX_PAGE_TOKEN = NameToken("index")

        val defaultDataRenderer: SiteBuilder.(Data<*>) -> Unit = { data ->
            if (data.type == typeOf<HtmlData>()) {
                page {
                    @Suppress("UNCHECKED_CAST")
                    val pageFragment: HtmlFragment = runBlocking { data.await() as HtmlFragment }
                    pageFragment.invoke(consumer)
                }
            }
        }
    }
}

object DefaultSiteLayout : SiteLayout {
    context(SiteBuilder) override fun render(item: DataTreeItem<*>) {
        pages(item)
    }
}