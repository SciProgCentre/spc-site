package space.kscience.snark

import io.ktor.http.ContentType
import kotlinx.html.div
import kotlinx.html.unsafe
import space.kscience.dataforge.meta.Meta
import kotlin.reflect.KType
import kotlin.reflect.typeOf

object SnarkHtmlParser:SnarkParser<HtmlFragment> {
    override val contentType: ContentType = ContentType.Text.Html
    override val fileExtensions: Set<String> = setOf("html")
    override val resultType: KType = typeOf<HtmlFragment>()

    override suspend fun parse(bytes: ByteArray, meta: Meta): HtmlFragment = {
        div{
           unsafe { +bytes.decodeToString() }
        }
    }
}