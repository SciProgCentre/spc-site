package space.kscience.snark

import kotlinx.coroutines.runBlocking
import kotlinx.html.FlowContent
import kotlinx.html.TagConsumer
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.data.await
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.int
import space.kscience.dataforge.meta.string


//TODO replace by VisionForge type
typealias HtmlFragment = TagConsumer<*>.() -> Unit

typealias HtmlData = Data<HtmlFragment>

fun HtmlData(meta: Meta, content: TagConsumer<*>.() -> Unit): HtmlData = Data(content, meta)

val HtmlData.id: String get() = meta["id"]?.string ?: "block[${hashCode()}]"
val HtmlData.language: String? get() = meta["language"].string?.lowercase()

val HtmlData.order: Int? get() = meta["order"]?.int

fun TagConsumer<*>.htmlData(data: HtmlData) = runBlocking {
    data.await().invoke(this@htmlData)
}

fun FlowContent.htmlData(data: HtmlData) = runBlocking {
    data.await().invoke(consumer)
}

