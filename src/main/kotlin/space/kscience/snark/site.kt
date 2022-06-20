package ru.mipt.spc

//class SiteBuilderAction : AbstractAction<Any, HtmlFragment>(typeOf<HtmlFragment>()) {
//
//    private val pageBuilders = HashMap<Name, (DataSet<*>) -> HtmlData>()
//
//    fun page(name: Name, meta: Meta = Meta.EMPTY, builder: context(PageContext) TagConsumer<*>.() -> Unit) {
//        val prefix = name.tokens.joinToString(separator = "/", prefix = "/")
//        pageBuilders[name] = { dataset ->
//            val fragment: HtmlFragment = {
//                builder.invoke(PageContext(prefix, dataset), this)
//            }
//            Data(fragment, meta.copy {
//                "name" put name.toString()
//            })
//        }
//    }
//
//
//    override fun DataSetBuilder<HtmlFragment>.generate(data: DataSet<Any>, meta: Meta) {
//        pageBuilders.forEach { (name, builder) ->
//            data(name, builder(data))
//        }
//    }
//
//}