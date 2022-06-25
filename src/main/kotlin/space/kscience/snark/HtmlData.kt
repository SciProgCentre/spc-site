package space.kscience.snark

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.html.FlowContent
import kotlinx.html.TagConsumer
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.data.await
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.int
import space.kscience.dataforge.meta.string


//TODO replace by VisionForge type
//typealias HtmlFragment = context(PageBuilder, TagConsumer<*>) () -> Unit

fun interface HtmlFragment {
    fun TagConsumer<*>.renderFragment(page: PageBuilder)
    //TODO move pageBuilder to a context receiver after KT-52967 is fixed
}

typealias HtmlData = Data<HtmlFragment>

//fun HtmlData(meta: Meta, content: context(PageBuilder) TagConsumer<*>.() -> Unit): HtmlData =
//    Data(HtmlFragment(content), meta)

internal val HtmlData.id: String get() = meta["id"]?.string ?: "block[${hashCode()}]"
internal val HtmlData.language: String? get() = meta["language"].string?.lowercase()

internal val HtmlData.order: Int? get() = meta["order"]?.int

context(PageBuilder) fun FlowContent.htmlData(data: HtmlData) = runBlocking(Dispatchers.IO) {
    with(data.await()) { consumer.renderFragment(this@PageBuilder) }
}