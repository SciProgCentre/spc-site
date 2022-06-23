package space.kscience.snark

import space.kscience.dataforge.context.Context
import space.kscience.dataforge.context.ContextAware
import space.kscience.dataforge.data.*
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.*
import space.kscience.snark.SiteData.Companion.INDEX_PAGE_TOKEN
import kotlin.reflect.KType
import kotlin.reflect.typeOf

data class SiteData(
    val snark: SnarkPlugin,
    val data: DataTree<*>,
    val baseUrlPath: String,
    override val meta: Meta,
) : ContextAware, DataTree<Any> by data {

    override val context: Context get() = snark.context

    val language: String? by meta.string()

    companion object {
        fun empty(
            snark: SnarkPlugin,
            baseUrlPath: String = "",
            meta: Meta = Meta.EMPTY,
        ): SiteData {
            //TODO use empty data from DF
            val emptyData = object : DataTree<Any> {
                override val items: Map<NameToken, DataTreeItem<Any>> get() = emptyMap()
                override val dataType: KType get() = typeOf<Any>()
                override val meta: Meta get() = meta
            }
            return SiteData(snark, emptyData, baseUrlPath, meta)
        }

        val INDEX_PAGE_TOKEN: NameToken = NameToken("index")
    }
}

/**
 * Resolve a resource full path by its name
 */
fun SiteData.resolveRef(name: String): String = if (baseUrlPath.isEmpty()) {
    name
} else {
    "${baseUrlPath.removeSuffix("/")}/$name"
}

/**
 * Resolve a page designated by given name. Depending on rendering specifics, some prefixes or suffixes could be added.
 */
fun SiteData.resolvePage(name: Name): String {
    return resolveRef(name.tokens.joinToString("/")) + (meta["pageSuffix"].string ?: "")
}

/**
 *
 */
fun SiteData.resolvePage(name: String): String = resolvePage(name.parseAsName())

val SiteData.homeRef get() = resolvePage(Name.EMPTY)

/**
 * Resolve a Html builder by its full name
 */
fun DataTree<*>.resolveHtml(name: Name): HtmlData? {
    val resolved = (getByType<HtmlFragment>(name) ?: getByType<HtmlFragment>(name + INDEX_PAGE_TOKEN))

    return resolved?.takeIf {
        it.published //TODO add language confirmation
    }
}

/**
 * Find all Html blocks using given name/meta filter
 */
fun DataTree<*>.resolveAllHtml(predicate: (name: Name, meta: Meta) -> Boolean): Map<Name, HtmlData> =
    filterByType<HtmlFragment> { name, meta ->
        predicate(name, meta)
                && meta["published"].string != "false"
        //TODO add language confirmation
    }.asSequence().associate { it.name to it.data }


fun SiteData.findByType(contentType: String, baseName: Name = Name.EMPTY) = resolveAllHtml { name, meta ->
    name.startsWith(baseName) && meta["content_type"].string == contentType
}

internal val Data<*>.published: Boolean get() = meta["published"].string != "false"
//
//fun SnarkPlugin.readData(data: DataTree<*>, rootUrl: String = "/"): SiteData =
//    SiteData(this, data, rootUrl)
//
//fun SnarkPlugin.readDirectory(path: Path, rootUrl: String = "/"): SiteData {
//    val parsedData: DataTree<Any> = readDirectory(path)
//
//    return readData(parsedData, rootUrl)
//}
