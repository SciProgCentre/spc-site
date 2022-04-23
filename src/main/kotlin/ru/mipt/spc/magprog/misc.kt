package ru.mipt.spc.magprog

import kotlinx.css.CSSBuilder
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.style

fun CommonAttributeGroupFacade.css(block: CSSBuilder.() -> Unit) {
    style = CSSBuilder().block().toString()
}