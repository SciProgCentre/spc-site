package space.kscience.snark

import io.ktor.http.ContentType
import io.ktor.utils.io.core.Input
import kotlinx.html.div
import kotlinx.html.unsafe
import kotlin.reflect.KType
import kotlin.reflect.typeOf

object SnarkHtmlParser : SnarkParser<HtmlFragment> {
    override val contentType: ContentType = ContentType.Text.Html
    override val fileExtensions: Set<String> = setOf("html")
    override val type: KType = typeOf<HtmlFragment>()

    override fun readObject(input: Input): HtmlFragment = {
        div {
            unsafe { +input.readText() }
        }
    }
}