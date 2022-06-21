package space.kscience.snark

import kotlinx.coroutines.runBlocking
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.data.DataTreeItem
import space.kscience.dataforge.data.await
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.getIndexed
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.asName
import space.kscience.dataforge.names.plus
import space.kscience.snark.SiteLayout.Companion.DESIGNATION_KEY
import space.kscience.snark.SiteLayout.Companion.LAYOUT_KEY
import java.nio.file.Path
import kotlin.reflect.typeOf

internal fun SiteBuilder.staticFrom(rootMeta: Meta) {
    rootMeta.getIndexed("resource".asName()).forEach { (_, meta) ->

        val path by meta.string()
        val remotePath by meta.string()

        path?.let { resourcePath ->
            //If remote path provided, use a single resource
            remotePath?.let {
                staticResourceFile(it, resourcePath)
                return@forEach
            }


            //otherwise use package resources
            staticResourceDirectory(resourcePath)
        }
    }

    rootMeta.getIndexed("file".asName()).forEach { (_, meta) ->
        val remotePath by meta.string { error("File remote path is not provided") }
        val path by meta.string { error("File path is not provided") }
        staticFile(remotePath, Path.of(path))
    }

    rootMeta.getIndexed("directory".asName()).forEach { (_, meta) ->
        val path by meta.string { error("Directory path is not provided") }
        staticDirectory("", Path.of(path))
    }
}

/**
 * Represent pages in a [DataTree]
 */
fun SiteBuilder.data(data: DataTreeItem<*>, prefix: Name = Name.EMPTY) {
    val layoutMeta = data.meta[LAYOUT_KEY]
    if (layoutMeta != null) {
        //use layout if it is defined
        siteContext.snark.layout(layoutMeta).render(data)
    } else {
        when (data) {
            is DataTreeItem.Node -> {
                data.tree.items.forEach { (token, item) ->
                    data(item, prefix + token)
                }
            }
            is DataTreeItem.Leaf -> {
                val item = data.data
                if (item.type == typeOf<HtmlData>() && item.meta[DESIGNATION_KEY].string == "page") {
                    route(prefix.tokens.joinToString(separator = "/")) {
                        page {
                            @Suppress("UNCHECKED_CAST")
                            val pageFragment: HtmlFragment = runBlocking { item.await() as HtmlFragment }
                            pageFragment.invoke(consumer)
                        }
                        staticFrom(item.meta)
                    }
                }
            }
        }

    }
    //TODO watch for changes
}


fun interface SiteLayout {

    context(SiteBuilder) fun render(data: DataTreeItem<*>)

    companion object {
        internal const val DESIGNATION_KEY = "designation"
        const val LAYOUT_KEY = "layout"
    }
}

object DefaultSiteLayout : SiteLayout {
    context(SiteBuilder) override fun render(data: DataTreeItem<*>) {
        data(data)
    }
}