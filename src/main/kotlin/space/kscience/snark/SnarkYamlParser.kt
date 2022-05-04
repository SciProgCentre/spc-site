package space.kscience.snark

import io.ktor.http.ContentType
import space.kscience.dataforge.io.asBinary
import space.kscience.dataforge.io.readObject
import space.kscience.dataforge.io.yaml.YamlMetaFormat
import space.kscience.dataforge.meta.Meta
import kotlin.reflect.KType
import kotlin.reflect.typeOf

object SnarkYamlParser : SnarkParser<Meta> {
    override val contentType: ContentType = ContentType.Application.Json
    override val fileExtensions: Set<String> = setOf("yaml", "yml")
    override val resultType: KType = typeOf<Meta>()

    override suspend fun parse(bytes: ByteArray, meta: Meta): Meta =
        YamlMetaFormat.readObject(bytes.asBinary())
}