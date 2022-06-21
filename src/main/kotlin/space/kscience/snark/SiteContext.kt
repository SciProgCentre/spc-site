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
import space.kscience.dataforge.names.plus
import space.kscience.dataforge.names.startsWith
import space.kscience.snark.SiteContext.Companion.INDEX_PAGE_NAME
import java.nio.file.Path

data class SiteContext(
    val snark: SnarkPlugin,
    val path: String,
    val meta: Meta,
    val data: DataTree<*>,
) : ContextAware {

    override val context: Context get() = snark.context

    val language: String? by meta.string()

    companion object {
        const val INDEX_PAGE_NAME: String = "index"
    }
}

/**
 * Resolve a resource full path by its name
 */
fun SiteContext.resolveRef(name: String): String = "${path.removeSuffix("/")}/$name"

fun SiteContext.resolveRef(name: Name): String = "${path.removeSuffix("/")}/${name.tokens.joinToString("/")}"

/**
 * Resolve a Html builder by its full name
 */
fun SiteContext.resolveHtml(name: Name): HtmlData? {
    val resolved = (data.getByType<HtmlFragment>(name) ?: data.getByType<HtmlFragment>(name + INDEX_PAGE_NAME))

    return resolved?.takeIf {
        it.published //TODO add language confirmation
    }
}

/**
 * Find all Html blocks using given name/meta filter
 */
fun SiteContext.resolveAllHtml(predicate: (name: Name, meta: Meta) -> Boolean): Map<Name, HtmlData> =
    data.filterByType<HtmlFragment> { name, meta ->
        predicate(name, meta)
                && meta["published"].string != "false"
        //TODO add language confirmation
    }.asSequence().associate { it.name to it.data }

val SiteContext.homeRef get() = resolveRef("").removeSuffix("/")


fun SiteContext.findByType(contentType: String, baseName: Name = Name.EMPTY) = resolveAllHtml { name, meta ->
    name.startsWith(baseName) && meta["content_type"].string == contentType
}

internal val Data<*>.published: Boolean get() = meta["published"].string != "false"

fun SnarkPlugin.siteContext(rootUrl: String, data: DataTree<*>): SiteContext =
    SiteContext(this, rootUrl, data.meta, data)

fun SnarkPlugin.read(path: Path, rootUrl: String = "/"): SiteContext {
    val parsedData: DataTree<Any> = readDirectory(path)

    return siteContext(rootUrl, parsedData)
}

@PublishedApi
internal fun SiteContext.copyWithRequestHost(request: ApplicationRequest): SiteContext {
    val uri = URLBuilder(
        protocol = URLProtocol.createOrDefault(request.origin.scheme),
        host = request.host(),
        port = request.port(),
        pathSegments = path.split("/"),
    )
    return copy(path = uri.buildString())
}

/**
 * Substitute uri in [SiteContext] with uri in the call to properly resolve relative refs. Only host properties are substituted.
 */
context(SiteContext) inline fun withRequest(request: ApplicationRequest, block: context(SiteContext) () -> Unit) {
    block(copyWithRequestHost(request))
}