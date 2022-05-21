package space.kscience.snark

import io.ktor.http.ContentType
import io.ktor.util.extension
import io.ktor.utils.io.core.readBytes
import space.kscience.dataforge.context.*
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.io.IOReader
import space.kscience.dataforge.io.JsonMetaFormat
import space.kscience.dataforge.io.asBinary
import space.kscience.dataforge.io.yaml.YamlMetaFormat
import space.kscience.dataforge.io.yaml.YamlPlugin
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.misc.DFExperimental
import space.kscience.dataforge.misc.Type
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.asName
import space.kscience.dataforge.workspace.FileData
import space.kscience.dataforge.workspace.readDataDirectory
import java.nio.file.Path
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Type(SnarkParser.TYPE)
interface SnarkParser<out R : Any> : IOReader<R> {
    val contentType: ContentType

    val fileExtensions: Set<String>

    val priority: Int get() = DEFAULT_PRIORITY

    suspend fun parse(bytes: ByteArray, meta: Meta): R = bytes.asBinary().read {
        readObject(this)
    }

    companion object {
        const val TYPE = "snark.parser"
        const val DEFAULT_PRIORITY = 10
    }
}

@PublishedApi
internal class SnarkParserWrapper<R : Any>(
    val reader: IOReader<R>,
    override val type: KType,
    override val contentType: ContentType,
    override val fileExtensions: Set<String>,
) : SnarkParser<R>, IOReader<R> by reader


inline fun <reified R : Any> SnarkParser(
    reader: IOReader<R>,
    contentType: ContentType,
    vararg fileExtensions: String,
): SnarkParser<R> = SnarkParserWrapper(reader, typeOf<R>(), contentType, fileExtensions.toSet())

@OptIn(DFExperimental::class)
class SnarkPlugin : AbstractPlugin() {
    private val yaml by require(YamlPlugin)
    val io get() = yaml.io

    override val tag: PluginTag get() = Companion.tag

    private val parsers: Map<Name, SnarkParser<*>> by lazy {
        context.gather(SnarkParser.TYPE, true)
    }

//    val parseAction: Action<ByteArray, Any> = Action.map {
//        val parser: SnarkParser<*>? = parsers.values.filter { parser ->
//            parser.contentType.toString() == meta["contentType"].string ||
//                    meta[META_FILE_EXTENSION_KEY].string in parser.fileExtensions
//        }.maxByOrNull {
//            it.priority
//        }
//
//        //ensure that final type is correct
//        if (parser == null) {
//            logger.warn { "The parser is not found for data with meta $meta" }
//            result { it }
//        } else {
//            result(parser.resultType) { bytes ->
//                parser.parse(bytes, meta)
//            }
//        }
//    }

    fun readDirectory(path: Path): DataTree<Any> = io.readDataDirectory(path) { dataPath, meta ->
        val fileExtension = meta[FileData.META_FILE_EXTENSION_KEY].string ?: dataPath.extension
        val parser: SnarkParser<*>? = parsers.values.filter { parser ->
            fileExtension in parser.fileExtensions
        }.maxByOrNull {
            it.priority
        }

        parser ?: run {
            logger.warn { "The parser is not found for file $dataPath with meta $meta" }
            byteArrayIOReader
        }
    }


    override fun content(target: String): Map<Name, Any> = when (target) {
        SnarkParser.TYPE -> mapOf(
            "html".asName() to SnarkHtmlParser,
            "markdown".asName() to SnarkMarkdownParser,
            "json".asName() to SnarkParser(JsonMetaFormat, ContentType.Application.Json, "json"),
            "yaml".asName() to SnarkParser(YamlMetaFormat, ContentType.Application.Json, "yaml", "yml")
        )
        else -> super.content(target)
    }

    companion object : PluginFactory<SnarkPlugin> {
        override val tag: PluginTag = PluginTag("snark")
        override val type: KClass<out SnarkPlugin> = SnarkPlugin::class

        override fun build(context: Context, meta: Meta): SnarkPlugin = SnarkPlugin()

        private val byteArrayIOReader = IOReader {
            readBytes()
        }
    }
}