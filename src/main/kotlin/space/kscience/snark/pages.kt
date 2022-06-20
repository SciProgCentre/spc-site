package space.kscience.snark

import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.get
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.data.DataTreeItem
import space.kscience.dataforge.data.await
import space.kscience.dataforge.data.type
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.getIndexed
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.asName
import space.kscience.dataforge.names.plus
import java.nio.file.Path
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.reflect.typeOf

internal const val DESIGNATION_KEY = "designation"

private fun SnarkRoute.staticFrom(rootMeta: Meta) {
//    rootMeta.getIndexed("resource".asName()).forEach { (_, meta) ->
//        val resourcePackage by meta.string()
//        val remotePath by meta.string()
//        val resourceName by meta.string()
//
//        //If remote path provided, use a single resource
//        remotePath?.let {
//            resource(it, resourceName ?: it, resourcePackage)
//            return@forEach
//        }
//
//        //otherwise use package resources
//        resources(resourcePackage)
//    }

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
context(PageContext) fun SnarkRoute.pagesFrom(prefix: Name, data: DataTree<*>) {
    if (data.meta[DESIGNATION_KEY].string == "page") {
        TODO("Implement node-based pages")
//        route(prefix.tokens.joinToString(separator = "/")) {
//            get {
//                val headFragment = data.getByType<HtmlFragment>("head")?.await()
//                call.respondHtml {
//                    head {
//                        headFragment?.invoke(consumer)
//                        data.meta["title"].string?.let { title(it) }
//                    }
//                    body {
//                        data.filterByType<HtmlFragment> { name, meta ->
//                            name.first().body == "section" && meta["published"].boolean != false
//                        }.traverse().sortedBy {  }
//                    }
//                }
//            }
//            staticFrom(data.meta)
//        }
    } else {
        data.items.forEach { (token, item) ->
            when (item) {
                is DataTreeItem.Node -> pagesFrom(prefix + token, item.tree)
                is DataTreeItem.Leaf -> if (item.type == typeOf<HtmlData>() && item.meta[DESIGNATION_KEY].string == "page") {
                    route(prefix.tokens.joinToString(separator = "/")) {
                        get {
                            @Suppress("UNCHECKED_CAST")
                            val pageFragment: HtmlFragment = item.data.await() as HtmlFragment
                            call.respondHtml {
                                pageFragment.invoke(consumer)
                            }
                        }
                        staticFrom(item.meta)
                    }
                }
            }
        }
    }
    //TODO watch for changes
}
