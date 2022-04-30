package ru.mipt.spc.magprog

import kotlinx.coroutines.runBlocking
import kotlinx.html.div
import kotlinx.html.unsafe
import kotlinx.serialization.json.Json
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import ru.mipt.spc.magprog.DirectoryDataTree.Companion.META_FILE_EXTENSION_KEY
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.data.*
import space.kscience.dataforge.io.asBinary
import space.kscience.dataforge.io.readObject
import space.kscience.dataforge.io.yaml.YamlMetaFormat
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.meta.toMeta
import space.kscience.dataforge.misc.DFInternal
import space.kscience.dataforge.names.Name
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class DataSetSiteContext(
    override val context: Context,
    val prefix: String,
    val dataSet: DataSet<Any>,
) : SiteContext {

    override fun resolveResource(name: String): String = "$prefix/$name"

    private val markdownFlavor = CommonMarkFlavourDescriptor()
    private val markdownParser = MarkdownParser(markdownFlavor)

    //TODO replace by a plugin
    private suspend fun Data<ByteArray>.toHtmlBlock(): HtmlBlock {
        val fileType = meta[META_FILE_EXTENSION_KEY].string
        val src = await().decodeToString()

        return when (fileType) {
            "html" -> HtmlBlock(meta) {
                div {
                    unsafe {
                        +src
                    }
                }
            }

            "markdown", "mdown", "mkdn", "mkd", "md" -> HtmlBlock(meta) {
                div("markdown") {
                    val parsedTree = markdownParser.buildMarkdownTreeFromString(src)

                    unsafe {
                        +HtmlGenerator(src, parsedTree, markdownFlavor).generateHtml()
                    }
                }
            }
            else -> error("Can't convert a data with file extension $fileType to html")
        }

    }

    private val Data<*>.published: Boolean get() = meta["published"].string != "false"

    @DFInternal
    override fun <T : Any> resolve(type: KType, name: Name): Data<T>? = when (type) {
        typeOf<Meta>() -> {
            val data = dataSet.selectOne<ByteArray>(name)
            if( data == null) null else {
                when (data.meta[META_FILE_EXTENSION_KEY].string) {
                    "json" -> data.map {
                        Json.parseToJsonElement(it.decodeToString()).toMeta()
                    }
                    "yaml" -> data.map {
                        YamlMetaFormat.readObject(it.asBinary())
                    }
                    else -> error("File with extension ${data.meta[META_FILE_EXTENSION_KEY]} could not be parsed as Meta")
                } as Data<T>?
            }
        }
        else -> dataSet.selectOne(type, name)
    }

    @DFInternal
    override fun <T : Any> resolveAll(type: KType, filter: (name: Name, meta: Meta) -> Boolean): DataSet<T> =
        dataSet.select(type, filter = filter)

    override fun resolveHtml(name: Name): HtmlBlock? = runBlocking {
        resolve<ByteArray>(name)?.takeIf { it.published }?.toHtmlBlock()
    }

    override fun resolveAllHtml(filter: (name: Name, meta: Meta) -> Boolean): Map<Name, HtmlBlock> = runBlocking {
        buildMap {
            resolveAll<ByteArray>(filter).dataSequence().filter { it.published }.forEach {
                put(it.name, it.toHtmlBlock())
            }
        }
    }

}