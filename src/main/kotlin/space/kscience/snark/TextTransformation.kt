package space.kscience.snark

import space.kscience.dataforge.misc.Type
import space.kscience.dataforge.names.NameToken

@Type(TextTransformation.TYPE)
fun interface TextTransformation {
    context(PageBuilder) fun transform(text: String): String

    companion object {
        const val TYPE = "snark.textTransformation"
        val TEXT_TRANSFORMATION_KEY = NameToken("transformation")

        val replaceLinks: TextTransformation = object : TextTransformation {
            context(PageBuilder) override fun transform(text: String): String {
                return text.replace("\${homeRef}", homeRef)
            }
        }
    }
}


