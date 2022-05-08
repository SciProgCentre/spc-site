package ru.mipt.spc.magprog

import space.kscience.dataforge.actions.invoke
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.data.DataSet
import space.kscience.dataforge.data.filterByType
import space.kscience.dataforge.data.getByType
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.misc.DFInternal
import space.kscience.dataforge.names.Name
import space.kscience.snark.DirectoryDataTree
import space.kscience.snark.HtmlData
import space.kscience.snark.HtmlFragment
import space.kscience.snark.SnarkPlugin
import java.nio.file.Path
import kotlin.reflect.KType

class SnarkPageContext(
    val snarkPlugin: SnarkPlugin,
    val path: Path,
    val prefix: String,
) : PageContext {
    override val context: Context get() = snarkPlugin.context

    override fun resolveRef(name: String): String = "$prefix/$name"

    private val directoryDataTree by lazy { DirectoryDataTree(snarkPlugin.io, path) }

    private val parsedData: DataSet<Any> by lazy { snarkPlugin.parseAction(directoryDataTree) }

    @DFInternal
    override fun <T : Any> resolve(type: KType, name: Name): Data<T>? = parsedData.getByType(type, name)

    @DFInternal
    override fun <T : Any> resolveAll(type: KType, predicate: (name: Name, meta: Meta) -> Boolean): DataSet<T> =
        parsedData.filterByType(type, predicate)

    override fun resolveHtml(name: Name): HtmlData? = resolve(name)

    override fun resolveAllHtml(filter: (name: Name, meta: Meta) -> Boolean): Map<Name, HtmlData> =
        resolveAll<HtmlFragment>(filter).traverse().filter { it.published }.associate { it.name to it.data }
}