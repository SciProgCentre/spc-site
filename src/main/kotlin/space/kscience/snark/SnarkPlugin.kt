package space.kscience.snark

import io.ktor.http.ContentType
import space.kscience.dataforge.actions.Action
import space.kscience.dataforge.actions.map
import space.kscience.dataforge.context.*
import space.kscience.dataforge.io.yaml.YamlPlugin
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.misc.DFExperimental
import space.kscience.dataforge.misc.Type
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.asName
import kotlin.reflect.KClass
import kotlin.reflect.KType

@Type(SnarkParser.TYPE)
interface SnarkParser<R : Any> {
    val contentType: ContentType

    val fileExtensions: Set<String>

    val priority: Int get() = DEFAULT_PRIORITY

    val resultType: KType

    suspend fun parse(bytes: ByteArray, meta: Meta): R

    companion object {
        const val TYPE = "snark.parser"
        const val DEFAULT_PRIORITY = 10
    }
}


@OptIn(DFExperimental::class)
class SnarkPlugin : AbstractPlugin() {
    private val yaml by require(YamlPlugin)
    val io get() = yaml.io

    override val tag: PluginTag get() = Companion.tag

    private val parsers: Map<Name, SnarkParser<*>> by lazy {
        context.gather(SnarkParser.TYPE, true)
    }

    val parseAction = Action.map {
        val parser: SnarkParser<*>? = parsers.values.filter { parser ->
            parser.contentType.toString() == meta["contentType"].string ||
                    meta[DirectoryDataTree.META_FILE_EXTENSION_KEY].string in parser.fileExtensions
        }.maxByOrNull {
            it.priority
        }

        //ensure that final type is correct
        if (parser == null) {
            logger.warn { "The parser is not found for data with meta $meta" }
            result { it }
        } else {
            result(parser.resultType) { bytes ->
                parser.parse(bytes, meta)
            }
        }
    }

    override fun content(target: String): Map<Name, Any> = when (target) {
        SnarkParser.TYPE -> mapOf(
            "html".asName() to SnarkHtmlParser,
            "markdown".asName() to SnarkMarkdownParser,
            "json".asName() to SnarkJsonParser,
            "yaml".asName() to SnarkYamlParser
        )
        else -> super.content(target)
    }

    companion object : PluginFactory<SnarkPlugin> {
        override val tag: PluginTag = PluginTag("snark")
        override val type: KClass<out SnarkPlugin> = SnarkPlugin::class

        override fun build(context: Context, meta: Meta): SnarkPlugin = SnarkPlugin()
    }
}