package ru.mipt.spc.magprog

import space.kscience.dataforge.context.ContextAware
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.data.DataSet
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.misc.DFInternal
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.startsWith
import space.kscience.snark.HtmlData
import kotlin.reflect.KType
import kotlin.reflect.typeOf

interface PageContext: ContextAware {

    /**
     * Resolve a resource full path by its name
     */
    fun resolveResource(name: String): String

    @DFInternal
    fun <T: Any> resolve(type: KType, name: Name): Data<T>?

    @DFInternal
    fun <T: Any> resolveAll(type: KType, filter: (name: Name, meta: Meta) -> Boolean): DataSet<T>

    /**
     * Resolve a Html builder by its full name
     */
    fun resolveHtml(name: Name): HtmlData?

    /**
     * Find all Html blocks using given name/meta filter
     */
    fun resolveAllHtml(filter: (name: Name, meta: Meta) -> Boolean): Map<Name, HtmlData>
}

@OptIn(DFInternal::class)
inline fun <reified T: Any> PageContext.resolve(name: Name): Data<T>? = resolve(typeOf<T>(), name)

@OptIn(DFInternal::class)
inline fun <reified T:Any> PageContext.resolveAll(noinline filter: (name: Name, meta: Meta) -> Boolean): DataSet<T> =
    resolveAll(typeOf<T>(), filter)

fun PageContext.findByType(contentType: String, baseName: Name = Name.EMPTY) = resolveAllHtml { name, meta ->
    name.startsWith(baseName) && meta["content_type"].string == contentType
}