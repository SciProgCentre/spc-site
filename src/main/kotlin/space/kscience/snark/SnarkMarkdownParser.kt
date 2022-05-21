package space.kscience.snark

import io.ktor.http.ContentType
import io.ktor.utils.io.core.Input
import kotlinx.html.div
import kotlinx.html.unsafe
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import kotlin.reflect.KType
import kotlin.reflect.typeOf

object SnarkMarkdownParser:SnarkParser<HtmlFragment> {
    override val contentType: ContentType = ContentType.Text.Html
    override val fileExtensions: Set<String> = setOf("markdown", "mdown", "mkdn", "mkd", "md")
    override val type: KType = typeOf<HtmlFragment>()

    private val markdownFlavor = CommonMarkFlavourDescriptor()
    private val markdownParser = MarkdownParser(markdownFlavor)

    override fun readObject(input: Input): HtmlFragment  {
        val src = input.readText()
        val parsedTree = markdownParser.buildMarkdownTreeFromString(src)
        val htmlString = HtmlGenerator(src, parsedTree, markdownFlavor).generateHtml()

        return {
            div{
                unsafe {
                    +htmlString
                }
            }
        }
    }
}