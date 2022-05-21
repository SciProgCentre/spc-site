package space.kscience.snark

import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.server.plugins.origin
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.host
import io.ktor.server.request.port
import space.kscience.dataforge.data.*
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.startsWith
import java.nio.file.Path

data class PageContext(val path: String, val pageMeta: Meta, val data: DataSet<*>) {
    val language: String? by pageMeta.string()
}

/**
 * Resolve a resource full path by its name
 */
fun PageContext.resolveRef(name: String): String = "${path.removeSuffix("/")}/$name"

fun PageContext.resolveRef(name: Name): String = "${path.removeSuffix("/")}/${name.tokens.joinToString ("/")}"

/**
 * Resolve a Html builder by its full name
 */
fun PageContext.resolveHtml(name: Name): HtmlData? = data.getByType<HtmlFragment>(name)?.takeIf {
    it.published //TODO add language confirmation
}

/**
 * Find all Html blocks using given name/meta filter
 */
fun PageContext.resolveAllHtml(predicate: (name: Name, meta: Meta) -> Boolean): Map<Name, HtmlData> =
    data.filterByType<HtmlFragment> { name, meta ->
        predicate(name, meta)
                && meta["published"].string != "false"
        //TODO add language confirmation
    }.asSequence().associate { it.name to it.data }

val PageContext.homeRef get() = resolveRef("").removeSuffix("/")


fun PageContext.findByType(contentType: String, baseName: Name = Name.EMPTY) = resolveAllHtml { name, meta ->
    name.startsWith(baseName) && meta["content_type"].string == contentType
}

internal val Data<*>.published: Boolean get() = meta["published"].string != "false"

fun PageContext(rootUrl: String, data: DataSet<*>): PageContext = PageContext(rootUrl, data.meta, data)

fun SnarkPlugin.parse(rootUrl: String, path: Path): PageContext {
    val parsedData: DataSet<Any> = readDirectory(path)

    return PageContext(rootUrl, parsedData)
}

context(PageContext) inline fun withRequest(request: ApplicationRequest, block: context(PageContext) () -> Unit) {
    val uri = URLBuilder(
        protocol = URLProtocol.createOrDefault(request.origin.scheme),
        host = request.host(),
        port = request.port(),
        pathSegments = path.split("/"),
    )
    block(copy(path = uri.buildString()))
}