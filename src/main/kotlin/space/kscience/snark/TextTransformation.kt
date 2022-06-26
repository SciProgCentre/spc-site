package space.kscience.snark

import space.kscience.dataforge.misc.Type
import space.kscience.dataforge.names.NameToken

@Type(TextTransformation.TYPE)
fun interface TextTransformation {
    context(PageBuilder) fun transform(text: String): String

    companion object {
        const val TYPE = "snark.textTransformation"
        val TEXT_TRANSFORMATION_KEY = NameToken("transformation")
    }
}

object BasicTextTransformation : TextTransformation {

    private val regex = "\\\$\\{(\\w*)(?>\\(\"(.*)\"\\))?\\}".toRegex()

    context(PageBuilder) override fun transform(text: String): String {
        return text.replace(regex) { match ->
            when (match.groups[1]!!.value) {
                "homeRef" -> homeRef
                "resolveRef" -> {
                    val refString = match.groups[2]?.value ?: error("resolveRef requires a string (quoted) argument")
                    resolveRef(refString)
                }
                "resolvePageRef" -> {
                    val refString = match.groups[2]?.value ?: error("resolvePageRef requires a string (quoted) argument")
                    resolvePageRef(refString)
                }
                else -> match.value
            }
        }
    }
}


