package ru.mipt.spc.magprog

import io.ktor.util.InternalAPI
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.data.DataSet
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.startsWith
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface SiteContext {
    /**
     * Resolve a resource full path by its name
     */
    fun resolveResource(name: String): String

    @InternalAPI
    fun <T: Any> resolve(type: KType, name: Name): Data<T>?

    @InternalAPI
    fun <T: Any> resolveAll(type: KType, filter: (name: Name, meta: Meta) -> Boolean): DataSet<T>

    /**
     * Resolve a Html builder by its full name
     */
    fun resolveHtml(name: Name): HtmlBlock?

    /**
     * Find all Html blocks using given name/meta filter
     */
    fun resolveAllHtml(filter: (name: Name, meta: Meta) -> Boolean): Map<Name, HtmlBlock>
}

@OptIn(InternalAPI::class)
inline fun <reified T: Any> SiteContext.resolve(name: Name): Data<T>? = resolve(typeOf<T>(), name)

@OptIn(InternalAPI::class)
inline fun <reified T:Any> SiteContext.resolveAll(noinline filter: (name: Name, meta: Meta) -> Boolean): DataSet<T> =
    resolveAll(typeOf<T>(), filter)

fun SiteContext.findByType(contentType: String, baseName: Name = Name.EMPTY) = resolveAllHtml { name, meta ->
    name.startsWith(baseName) && meta["content_type"].string == contentType
}