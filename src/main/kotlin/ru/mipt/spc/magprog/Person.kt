package ru.mipt.spc.magprog

import kotlinx.css.*
import kotlinx.html.*
import space.kscience.dataforge.meta.string

class Person(val block: HtmlBlock) : HtmlBlock by block {
    val name: String by meta.string { error("Mentor name is not defined") }
    val photo: String? by meta.string()
}

context(SiteContext)
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
                    with(mentor) { content() }
                }
            }
        }
    }
}

context(SiteContext)
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

context(SiteContext)
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
