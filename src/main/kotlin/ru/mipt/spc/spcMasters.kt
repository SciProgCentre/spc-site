package ru.mipt.spc

import kotlinx.coroutines.runBlocking
import kotlinx.html.*
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.data.await
import space.kscience.dataforge.data.getByType
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.getIndexed
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.asName
import space.kscience.dataforge.names.plus
import space.kscience.dataforge.names.withIndex
import space.kscience.snark.*
import space.kscience.snark.html.*
import space.kscience.snark.html.WebPage
import java.nio.file.Path
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set


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

private val HtmlData.imagePath: String? get() = meta["image"]?.string ?: meta["image.path"].string
private val HtmlData.name: String get() = meta["name"].string ?: error("Name not found")

context(WebPage) class MagProgSection(
    val id: String,
    val title: String,
    val style: String,
    val content: FlowContent.() -> Unit,
)

context(WebPage) private fun wrapSection(
    id: String,
    title: String,
    sectionContent: FlowContent.() -> Unit,
): MagProgSection = MagProgSection(id, title, "wrapper style1 fullscreen fade-up") {
    div("inner") {
        h2 { +title }
        sectionContent()
    }
}

context(WebPage) private fun wrapSection(
    block: HtmlData,
    idOverride: String? = null,
): MagProgSection = wrapSection(
    idOverride ?: block.id,
    block.meta["section_title"]?.string ?: error("Section without title"),
) {
    htmlData(block)
}

private val CONTENT_NODE_NAME = Name.EMPTY//"content".asName()
private val INTRO_PATH: Name = CONTENT_NODE_NAME + "intro"
private val ENROLL_PATH: Name = CONTENT_NODE_NAME + "enroll"
private val CONTACTS_PATH: Name = CONTENT_NODE_NAME + "contacts"
private val PROGRAM_PATH: Name = CONTENT_NODE_NAME + "program"
private val RECOMMENDED_COURSES_PATH: Name = CONTENT_NODE_NAME + "recommendedCourses"
private val PARTNERS_PATH: Name = CONTENT_NODE_NAME + "partners"

context(WebPage) private fun FlowContent.programSection() {
    val programBlock = data.resolveHtml(PROGRAM_PATH)!!
    val recommendedBlock = data.resolveHtml(RECOMMENDED_COURSES_PATH)!!
    div("inner") {
        h2 { +"Учебная программа" }
        htmlData(programBlock)
        button(classes = "fit collapsible") {
            attributes["data-target"] = "recommended-courses-content"
            +"Рекомендованные курсы"
        }
        div(classes = "collapsible-content") {
            id = "recommended-courses-content"
            htmlData(recommendedBlock)
        }
    }
}

context(WebPage) private fun FlowContent.partners() {
    //val partnersData: Meta = resolve<Any>(PARTNERS_PATH)?.meta ?: Meta.EMPTY
    val partnersData: Meta = runBlocking { data.getByType<Meta>(PARTNERS_PATH)?.await() } ?: Meta.EMPTY
    div("inner") {
        h2 { +"Партнеры" }
        div("features") {
            partnersData.getIndexed("content".asName()).values.forEach { partner ->
                section {
                    a(href = partner["link"].string, target = "_blank") {
                        rel = "noreferrer"
                        val imagePath = partner["logo"].string?.let { resolveRef(it) }
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

//class Person(val data: HtmlData) : HtmlData by data {
//    val name: String by meta.string { error("Mentor name is not defined") }
//    val photo: String? by meta.string()
//}

context(WebPage) private fun FlowContent.team() {
    val team = data.findByContentType("magprog_team").values.sortedBy { it.order }

    div("inner") {
        h2 { +"Команда" }
        div("features") {
            team.forEach { member ->
                section {
                    a {
                        val imagePath = member.imagePath?.let { resolveRef(it) }
                        img(
                            classes = "icon major",
                            src = imagePath,
                            alt = imagePath
                        ) {
                            h3 { +member.name }
                            htmlData(member)
                        }
                    }
                }
            }
        }
    }

//    div("inner") {
//        h2 {
//            +"Команда"
//        }
//    }
//    team.forEach { member ->
//        section {
//            id = member.id
//            a(classes = "image") {
//                member.photo?.let { photoPath ->
//                    img(
//                        src = resolveRef(photoPath),
//                        alt = member.name
//                    ) {
//                        attributes["data-position"] = "center center"
//                    }
//                }
//            }
//
//            div("content") {
//                div("inner") {
//                    h3 {
//                        a(href = "#team_${member.id}") { +member.name }
//                    }
//                    htmlData(member)
//                }
//            }
//        }
//    }
}

context(WebPage) private fun FlowContent.mentors() {
    val mentors = data.findByContentType("magprog_mentor").entries.sortedBy { it.value.id }

    div("inner") {
        h2 {
            +"Научные руководители"
        }
    }
    mentors.forEach { (name, mentor) ->
        section {
            id = mentor.id
            val ref = resolvePageRef("mentor-${mentor.id}")
            a(classes = "image", href = ref) {
                mentor.imagePath?.let { photoPath ->
                    img(
                        src = resolveRef(photoPath),
                        alt = mentor.name
                    ) {
                        attributes["data-position"] = "center center"
                    }
                }
            }

            div("content") {
                div("inner") {
                    h2 {
                        a(href = ref) { +mentor.name }
                    }
                    val info = data.resolveHtml(name.withIndex("info"))
                    if (info != null) {
                        htmlData(info)
                    }
                }
            }
        }
    }
}

context(WebPage) internal fun HTML.magProgHead(title: String) {
    head {
        this.title = title
        meta {
            charset = "utf-8"
        }
        meta {
            name = "viewport"
            content = "width=device-width, initial-scale=1, user-scalable=no"
        }
        link {
            rel = "stylesheet"
            href = resolveRef("assets/css/main.css")
        }
        link {
            rel = "shortcut icon"
            href = resolveRef("images/favicon-32x32.png")
        }
        noScript {
            link {
                rel = "stylesheet"
                href = resolveRef("assets/css/noscript.css")
            }
        }
        link {
            rel = "apple-touch-icon"
            sizes = "180x180"
            href = "/apple-touch-icon.png"
        }
        link {
            rel = "icon"
            type = "image/png"
            sizes = "32x32"
            href = "/favicon-32x32.png"
        }
        link {
            rel = "icon"
            type = "image/png"
            sizes = "16x16"
            href = "/favicon-16x16.png"
        }
        link {
            rel = "manifest"
            href = "/site.webmanifest"
        }
    }
}

context(WebPage) internal fun BODY.magProgFooter() {
    footer("wrapper style1-alt") {
        id = "footer"
        div("inner") {
            ul("menu") {
                li { +"""SPC. All rights reserved.""" }
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
        src = resolveRef("assets/js/jquery.min.js")
    }
    script {
        src = resolveRef("assets/js/jquery.scrollex.min.js")
    }
    script {
        src = resolveRef("assets/js/jquery.scrolly.min.js")
    }
    script {
        src = resolveRef("assets/js/browser.min.js")
    }
    script {
        src = resolveRef("assets/js/breakpoints.min.js")
    }
    script {
        src = resolveRef("assets/js/util.js")
    }
    script {
        src = resolveRef("assets/js/main.js")
    }
}

context(SnarkContext) private val HtmlData.mentorPageId get() = "mentor-${id}"

internal fun SiteBuilder.spcMasters(dataPath: Path, prefix: Name = "magprog".asName()) {

    val magProgData: DataTree<Any> = snark.readDirectory(dataPath.resolve("content"))

    route(prefix, magProgData, setAsRoot = true) {
        file(dataPath.resolve("assets"))
        file(dataPath.resolve("images"))
        file(dataPath.resolve("../common"), "")

        page {
            val sections = listOf<MagProgSection>(
                wrapSection(data.resolveHtml(INTRO_PATH)!!, "intro"),
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
                wrapSection(data.resolveHtml(ENROLL_PATH)!!, "enroll"),
                wrapSection(id = "contacts", title = "Контакты") {
                    htmlData(data.resolveHtml(CONTACTS_PATH)!!)
                    team()
                }
            )

            magProgHead("Магистратура \"Научное программирование\"")
            body("is-preload magprog-body") {
                section {
                    id = "sidebar"
                    div("inner") {
                        nav {
                            ul {
                                li {
                                    a(
                                        classes = "spc-home",
                                        href = resolvePageRef(Name.of("..") + SiteBuilder.INDEX_PAGE_TOKEN)
                                    ) {
                                        i("fa fa-home") {
                                            attributes["aria-hidden"] = "true"
                                        }
                                        +"SPC"
                                    }
                                }
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
                    sections.forEach { sec ->
                        section(sec.style) {
                            id = sec.id
                            with(sec) { content() }
                        }
                    }
                }
                magProgFooter()
            }
        }


        val mentors = data.findByContentType("magprog_mentor").values.sortedBy {
            it.order
        }

        mentors.forEach { mentor ->
            page(mentor.mentorPageId.asName()) {

                magProgHead("Научное программирование: ${mentor.name}")
                body("is-preload") {
                    header {
                        id = "header"
                        a(classes = "title") {
                            href = "$homeRef#mentors"
                            +"Научные руководители"
                        }
                        nav {
                            ul {
                                mentors.forEach {
                                    li {
                                        a {
                                            href = resolvePageRef(it.mentorPageId)
                                            +it.name.substringAfterLast(" ")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    div {
                        id = "wrapper"
                        section("wrapper") {
                            id = "main"
                            div("inner") {
                                h1("major") { +mentor.name }
                                val imageClass = mentor.meta["image.position"].string ?: "left"
                                span("image $imageClass") {
                                    mentor.imagePath?.let { photoPath ->
                                        img(
                                            src = resolveRef(photoPath),
                                            alt = mentor.name
                                        )
                                    }
                                }
                                htmlData(mentor)
                            }
                        }
                    }
                    magProgFooter()
                }
            }
        }
    }
}
