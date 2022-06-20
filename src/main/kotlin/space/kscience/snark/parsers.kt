package space.kscience.snark

import io.ktor.util.asStream
import io.ktor.utils.io.core.Input
import kotlinx.html.div
import kotlinx.html.unsafe
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import space.kscience.dataforge.io.IOReader
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import kotlin.reflect.KType
import kotlin.reflect.typeOf

internal object SnarkHtmlParser : SnarkParser<HtmlFragment> {
    override val fileExtensions: Set<String> = setOf("html")
    override val type: KType = typeOf<HtmlFragment>()

    override fun readObject(input: Input): HtmlFragment = {
        div {
            unsafe { +input.readText() }
        }
    }
}

internal object SnarkMarkdownParser : SnarkParser<HtmlFragment> {
    override val fileExtensions: Set<String> = setOf("markdown", "mdown", "mkdn", "mkd", "md")
    override val type: KType = typeOf<HtmlFragment>()

    private val markdownFlavor = CommonMarkFlavourDescriptor()
    private val markdownParser = MarkdownParser(markdownFlavor)

    override fun readObject(input: Input): HtmlFragment {
        val src = input.readText()
        val parsedTree = markdownParser.buildMarkdownTreeFromString(src)
        val htmlString = HtmlGenerator(src, parsedTree, markdownFlavor).generateHtml()

        return {
            div {
                unsafe {
                    +htmlString
                }
            }
        }
    }
}

internal object ImageIOReader : IOReader<BufferedImage> {
    override val type: KType get() = typeOf<BufferedImage>()

    override fun readObject(input: Input): BufferedImage = ImageIO.read(input.asStream())
}
