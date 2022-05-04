package space.kscience.snark

import io.ktor.http.ContentType
import kotlinx.html.div
import kotlinx.html.unsafe
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import space.kscience.dataforge.meta.Meta
import kotlin.reflect.KType
import kotlin.reflect.typeOf

object SnarkMarkdownParser:SnarkParser<HtmlFragment> {
    override val contentType: ContentType = ContentType.Text.Html
    override val fileExtensions: Set<String> = setOf("markdown", "mdown", "mkdn", "mkd", "md")
    override val resultType: KType = typeOf<HtmlFragment>()

    private val markdownFlavor = CommonMarkFlavourDescriptor()
    private val markdownParser = MarkdownParser(markdownFlavor)

    override suspend fun parse(bytes: ByteArray, meta: Meta): HtmlFragment = {
        val src = bytes.decodeToString()
        div{
            val parsedTree = markdownParser.buildMarkdownTreeFromString(src)

            unsafe {
                +HtmlGenerator(src, parsedTree, markdownFlavor).generateHtml()
            }
        }
    }
}