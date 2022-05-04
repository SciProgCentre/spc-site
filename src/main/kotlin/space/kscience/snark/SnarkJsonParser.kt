package space.kscience.snark

import io.ktor.http.ContentType
import kotlinx.serialization.json.Json
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.toMeta
import kotlin.reflect.KType
import kotlin.reflect.typeOf

object SnarkJsonParser: SnarkParser<Meta> {
    override val contentType: ContentType = ContentType.Application.Json
    override val fileExtensions: Set<String> = setOf("json")
    override val resultType: KType= typeOf<Meta>()

    override suspend fun parse(bytes: ByteArray, meta: Meta): Meta =
        Json.parseToJsonElement(bytes.decodeToString()).toMeta()
}