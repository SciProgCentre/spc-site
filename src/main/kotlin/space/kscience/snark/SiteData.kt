package space.kscience.snark

import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.server.plugins.origin
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.host
import io.ktor.server.request.port
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.context.ContextAware
import space.kscience.dataforge.data.*
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.NameToken
import space.kscience.dataforge.names.plus
import space.kscience.dataforge.names.startsWith
import space.kscience.snark.SiteData.Companion.INDEX_PAGE_NAME
import kotlin.reflect.KType
import kotlin.reflect.typeOf

data class SiteData(
    val snark: SnarkPlugin,
    val data: DataTree<*>,
    val baseUrlPath: String,
    override val meta: Meta = data.meta,
) : ContextAware, DataTree<Any> by data {

    override val context: Context get() = snark.context

    val language: String? by meta.string()

    companion object {
        fun empty(
            snark: SnarkPlugin,
            baseUrlPath: String = "/",
            meta: Meta = Meta.EMPTY,
        ): SiteData {
            val emptyData = object : DataTree<Any> {
                override val items: Map<NameToken, DataTreeItem<Any>> get() = emptyMap()
                override val dataType: KType get() = typeOf<Any>()
                override val meta: Meta get() = meta
            }
            return SiteData(snark, emptyData, baseUrlPath)
        }

        const val INDEX_PAGE_NAME: String = "index"
    }
}

/**
 * Resolve a resource full path by its name
 */
fun SiteData.resolveRef(name: String): String = "${baseUrlPath.removeSuffix("/")}/$name"

fun SiteData.resolveRef(name: Name): String = "${baseUrlPath.removeSuffix("/")}/${name.tokens.joinToString("/")}"

/**
 * Resolve a Html builder by its full name
 */
fun DataTree<*>.resolveHtml(name: Name): HtmlData? {
    val resolved = (getByType<HtmlFragment>(name) ?: getByType<HtmlFragment>(name + INDEX_PAGE_NAME))

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

val SiteData.homeRef get() = resolveRef("").removeSuffix("/")

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

@PublishedApi
internal fun SiteData.copyWithRequestHost(request: ApplicationRequest): SiteData {
    val uri = URLBuilder(
        protocol = URLProtocol.createOrDefault(request.origin.scheme),
        host = request.host(),
        port = request.port(),
        pathSegments = baseUrlPath.split("/"),
    )
    return copy(baseUrlPath = uri.buildString())
}
