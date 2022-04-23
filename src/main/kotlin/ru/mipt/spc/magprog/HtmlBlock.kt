package ru.mipt.spc.magprog

import kotlinx.html.FlowContent
import space.kscience.dataforge.meta.*

enum class Language {
    EN,
    RU
}

interface HtmlBlock {
    val meta: Meta
    val content: FlowContent.() -> Unit
}

val HtmlBlock.id: String get() = meta["id"]?.string ?: "block[${hashCode()}]"
val HtmlBlock.language: Language get() = meta["language"]?.enum<Language>() ?: Language.RU

val HtmlBlock.order: Int? get() = meta["order"]?.int

