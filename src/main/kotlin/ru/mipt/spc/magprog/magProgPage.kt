package ru.mipt.spc.magprog

import kotlinx.coroutines.runBlocking
import kotlinx.css.*
import kotlinx.html.*
import space.kscience.dataforge.data.await
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.getIndexed
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.asName
import space.kscience.dataforge.names.plus

//fun CssBuilder.magProgCss() {
//    rule(".magprog-body") {
//        rule(".magprog-header") {
//            display = Display.flex
//            alignItems = Align.center
//            justifyContent = JustifyContent.center
//            marginTop = 90.pt
//            marginLeft = 40.pt
//        }
//    }
//}

class MagProgSection(
    val id: String,
    val title: String,
    val style: String,
    val content: FlowContent.() -> Unit,
)  {
    val meta: Meta
        get() = Meta {
            "id" put id
            "title" put title
        }
}

private fun wrapSection(
    id: String,
    title: String,
    sectionContent: FlowContent.() -> Unit,
): MagProgSection = MagProgSection(id, title, "wrapper style1 fullscreen fade-up") {
    div("inner") {
        h2 { +title }
        sectionContent()
    }
}

private fun wrapSection(
    block: HtmlData,
    idOverride: String? = null,
): MagProgSection = wrapSection(
    idOverride ?: block.id,
    block.meta["section_title"]?.string ?: error("Section without title"),
){
    htmlData(block)
}

private val CONTENT_NODE_NAME = Name.EMPTY//"content".asName()
private val INTRO_PATH: Name = CONTENT_NODE_NAME + "intro"
private val ENROLL_PATH: Name = CONTENT_NODE_NAME + "enroll"
private val CONTACTS_PATH: Name = CONTENT_NODE_NAME + "contacts"
private val PROGRAM_PATH: Name = CONTENT_NODE_NAME + "program"
private val RECOMMENDED_COURSES_PATH: Name = CONTENT_NODE_NAME + "recommendedCourses"
private val PARTNERS_PATH: Name = CONTENT_NODE_NAME + "partners"

context(SiteContext) private fun FlowContent.programSection() {
    val programBlock = resolveHtml(PROGRAM_PATH)!!
    val recommendedBlock = resolveHtml(RECOMMENDED_COURSES_PATH)!!
    div("inner") {
        h2 { +"Учебная программа" }
        htmlData(programBlock)
        button(classes = "fit btn btn-primary btn-lg") {
            attributes["data-bs-toggle"] = "collapse"
            attributes["data-bs-target"] = "#recommended-courses-collapse-text"
            attributes["aria-expanded"] = "false"
            attributes["aria-controls"] = "recommended-courses-collapse-text"
            +"Рекомендованные курсы"
        }
        div("collapse pt-3") {
            id = "recommended-courses-collapse-text"
            div("card card-body") {
                htmlData(recommendedBlock)
            }
        }
    }
}

context(SiteContext) private fun FlowContent.partners() {
    //val partnersData: Meta = resolve<Any>(PARTNERS_PATH)?.meta ?: Meta.EMPTY
    val partnersData: Meta =  runBlocking { resolve<Meta>(PARTNERS_PATH)?.await()} ?: Meta.EMPTY
    div("inner") {
        h2 { +"Партнеры" }
        div("features") {
            partnersData.getIndexed("content".asName()).values.forEach { partner ->
                section {
                    a(href = partner["link"].string, target = "_blank") {
                        rel = "noreferrer"
                        val imagePath = partner["logo"].string?.let(::resolveResource)
                        img(
                            classes = "icon major",
                            src = imagePath,
                            alt = imagePath
                        ) {
                            h3 { +(partner["title"].string ?: "") }
                        }
                    }
                }
            }
        }
    }
}

context(SiteContext) fun HTML.magProgPage() {
    val sections = listOf<MagProgSection>(
        wrapSection(resolveHtml(INTRO_PATH)!!, "intro"),
        MagProgSection(
            id = "partners",
            title = "Партнеры",
            style = "wrapper style3 fullscreen fade-up"
        ) {
            partners()
        },
        // section(props.data.partners),
        MagProgSection(
            id = "mentors",
            title = "Научные руководители",
            style = "wrapper style2 spotlights",
        ) {
            mentors()
        },
        MagProgSection(
            id = "program",
            title = "Учебная программа",
            style = "wrapper style3 fullscreen fade-up"
        ) {
            programSection()
        },
        wrapSection(resolveHtml(ENROLL_PATH)!!, "enroll"),
        MagProgSection(
            id = "team",
            title = "Команда",
            style = "wrapper style2 spotlights",
        ) {
            team()
        },
        wrapSection(resolveHtml(CONTACTS_PATH)!!, "contacts"),
    )

    head {
        title = "Магистратура \"Научное программирование\""
        meta {
            charset = "utf-8"
        }
        meta {
            name = "viewport"
            content = "width=device-width, initial-scale=1, user-scalable=no"
        }
        link {
            rel = "stylesheet"
            href = resolveResource("css/main.css")
        }
        noScript {
            link {
                rel = "stylesheet"
                href = resolveResource("css/noscript.css")
            }
        }
    }
    body("is-preload magprog-body") {
        section {
            id = "sidebar"
            div("inner") {
                nav {
                    ul {
                        sections.forEach { section ->
                            li {
                                a(href = "#${section.id}") {
                                    +section.title
                                }
                            }
                        }
                    }
                }
            }
        }
        div {
            id = "wrapper"
            div("magprog-header") {
                css {
                    display = Display.flex
                    alignItems = Align.center
                    justifyContent = JustifyContent.center
                    marginTop = 90.pt
                    marginLeft = 40.pt
                }
                sections.forEach { sec ->
                    section(sec.style) {
                        id = sec.id
                        with(sec) { content() }
                    }
                }
            }
        }
        footer("wrapper style1-alt") {
            id = "footer"
            div("inner") {
                ul("menu") {
                    li { +"""&copy; SPC. All rights reserved.""" }
                    li {
                        +"""Design:"""
                        a {
                            href = "http://html5up.net"
                            +"""HTML5 UP"""
                        }
                    }
                }
            }
        }
        script {
            src = resolveResource("js/jquery.min.js")
        }
        script {
            src = resolveResource("js/jquery.scrollex.min.js")
        }
        script {
            src = resolveResource("js/jquery.scrolly.min.js")
        }
        script {
            src = resolveResource("js/browser.min.js")
        }
        script {
            src = resolveResource("js/breakpoints.min.js")
        }
        script {
            src = resolveResource("js/util.js")
        }
        script {
            src = resolveResource("js/main.js")
        }
    }
}