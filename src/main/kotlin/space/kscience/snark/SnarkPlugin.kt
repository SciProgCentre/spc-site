package space.kscience.snark

import io.ktor.http.ContentType
import space.kscience.dataforge.actions.Action
import space.kscience.dataforge.actions.map
import space.kscience.dataforge.context.*
import space.kscience.dataforge.io.yaml.YamlPlugin
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.misc.Type
import space.kscience.dataforge.names.Name
import kotlin.reflect.KClass
import kotlin.reflect.KType

@Type(SnarkParser.TYPE)
interface SnarkParser<R : Any> {
    val contentType: ContentType

    val fileExtensions: Set<String>

    val priority: Int

    val resultType: KType

    suspend fun parse(bytes: ByteArray): R

    companion object {
        const val TYPE = "snark.parser"
    }
}


class SnarkPlugin : AbstractPlugin() {
    val yaml by require(YamlPlugin)
    val io get() = yaml.io

    override val tag: PluginTag get() = Companion.tag

    private val parsers: Map<Name, SnarkParser<*>> by lazy { context.gather(SnarkParser.TYPE, true) }

    private val parseAction = Action.map<ByteArray, Any> {
        result { bytes ->
            parsers.values.filter { parser ->
                parser.contentType.toString() == meta["contentType"].string ||
                        meta[DirectoryDataTree.META_FILE_EXTENSION_KEY].string in parser.fileExtensions
            }.maxByOrNull {
                it.priority
            }?.parse(bytes) ?: bytes
        }
    }

    companion object : PluginFactory<SnarkPlugin> {
        override val tag: PluginTag = PluginTag("snark")
        override val type: KClass<out SnarkPlugin> = SnarkPlugin::class

        override fun build(context: Context, meta: Meta): SnarkPlugin = SnarkPlugin()
    }
}