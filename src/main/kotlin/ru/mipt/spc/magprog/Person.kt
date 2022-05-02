package ru.mipt.spc.magprog

import kotlinx.css.*
import kotlinx.html.*
import ru.mipt.css
import space.kscience.dataforge.meta.string
import space.kscience.snark.HtmlData
import space.kscience.snark.htmlData
import space.kscience.snark.id
import space.kscience.snark.order

class Person(val block: HtmlData) : HtmlData by block {
    val name: String by meta.string { error("Mentor name is not defined") }
    val photo: String? by meta.string()
}

context(PageContext)
private fun FlowContent.personCards(list: List<Person>, prefix: String) {
    list.forEach { mentor ->
        section {
            id = mentor.id
            div("image main") {
                mentor.photo?.let { photoPath ->
                    img(
                        src = resolveResource(photoPath).toString(),
                        alt = mentor.name
                    )
                }
            }
            div("content") {
                div("inner") {
                    h2 {
                        a(href = "#${prefix}_${mentor.id}") { +mentor.name }
                    }
                    htmlData(mentor.block)
                }
            }
        }
    }
}

context(PageContext)
fun FlowContent.mentors() {
    val mentors = findByType("magprog_mentor").values.map {
        Person(it)
    }.sortedBy { it.order }

    div("header") {
        css {
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.center
            marginLeft = 40.pt
        }
        h1("title") {
            +"Научные руководители"
        }
    }
    personCards(mentors,"mentor")
}

context(PageContext)
fun FlowContent.team() {
    val team = findByType("magprog_team").values.map { Person(it) }.sortedBy { it.order }

    div("header") {
        css {
            display = Display.flex
            alignItems = Align.center
            justifyContent = JustifyContent.center
            marginLeft = 40.pt
        }
        h1("title") {
            +"Команда"
        }
    }
    personCards(team,"team")
}
