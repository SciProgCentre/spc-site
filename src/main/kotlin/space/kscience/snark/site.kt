package ru.mipt.spc

import kotlinx.html.TagConsumer
import space.kscience.dataforge.actions.AbstractAction
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.data.DataSet
import space.kscience.dataforge.data.DataSetBuilder
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.copy
import space.kscience.dataforge.names.Name
import space.kscience.snark.*
import kotlin.reflect.typeOf

class SiteBuilderAction(
    val snark: SnarkPlugin,
) : AbstractAction<Any, HtmlFragment>(typeOf<HtmlFragment>()) {

    private val pageBuilders = HashMap<Name, (DataSet<*>) -> HtmlData>()

    fun page(name: Name, meta: Meta = Meta.EMPTY, builder: context(PageContext) TagConsumer<*>.() -> Unit) {
        val prefix = name.tokens.joinToString(separator = "/", prefix = "/")
        pageBuilders[name] = { dataset ->
            val fragment: HtmlFragment = {
                builder.invoke(snark.buildPageContext(prefix, dataset), this)
            }
            Data(fragment, meta.copy {
                "name" put name.toString()
            })
        }
    }


    override fun DataSetBuilder<HtmlFragment>.generate(data: DataSet<Any>, meta: Meta) {
        pageBuilders.forEach { (name, builder) ->
            data(name, builder(data))
        }
    }

}