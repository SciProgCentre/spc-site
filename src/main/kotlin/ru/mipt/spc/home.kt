package ru.mipt.spc

import html5up.forty.fortyScripts
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.log
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.files
import io.ktor.server.http.content.static
import io.ktor.server.routing.*
import kotlinx.html.*
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.context.fetch
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.parseAsName
import space.kscience.snark.*
import java.nio.file.Path


private const val SPC_TITLE = "Scientific Programming Centre"

context(PageContext) private fun HTML.spcHead(title: String = SPC_TITLE) {
    head {
        title {
            +title
        }
        meta {
            charset = "utf-8"
        }
        meta {
            name = "viewport"
            content = "width=device-width, initial-scale=1, user-scalable=no"
        }
        link(rel = "stylesheet", href = resolveRef("assets/css/main.css"))
        noScript {
            link(rel = "stylesheet", href = resolveRef("assets/css/noscript.css"))
        }
    }
}

context(PageContext) private fun FlowContent.spcHomeMenu() {
    nav {
        id = "menu"
        ul("links") {
            li {
                a {
                    href = homeRef
                    +"""Home"""
                }
            }
            li {
                a {
                    href = resolveRef("magprog")
                    +"""Master"""
                }
            }
            li {
                a {
                    href = resolveRef("research")
                    +"""Research"""
                }
            }
            li {
                a {
                    href = resolveRef("consulting")
                    +"""Consulting"""
                }
            }
            li {
                a {
                    href = resolveRef("team")
                    +"""Team"""
                }
            }
        }
//        ul("actions stacked") {
//            li {
//                a(classes = "button primary fit") {
//                    href = "#"
//                    +"""Get Started"""
//                }
//            }
//            li {
//                a(classes = "button fit") {
//                    href = "#"
//                    +"""Log In"""
//                }
//            }
//        }
    }
}

context(PageContext) private fun FlowContent.spcFooter() {
    footer {
        id = "footer"
        div("inner") {
            ul("icons") {
//                li {
//                    a(classes = "icon brands alt fa-twitter") {
//                        href = "#"
//                        span("label") { +"""Twitter""" }
//                    }
//                }
//                li {
//                    a(classes = "icon brands alt fa-facebook-f") {
//                        href = "#"
//                        span("label") { +"""Facebook""" }
//                    }
//                }
//                li {
//                    a(classes = "icon brands alt fa-instagram") {
//                        href = "#"
//                        span("label") { +"""Instagram""" }
//                    }
//                }
                li {
                    a(classes = "icon brands alt fa-github") {
                        href = "https://github.com/mipt-npm"
                        span("label") { +"""GitHub""" }
                    }
                }
//                li {
//                    a(classes = "icon brands alt fa-linkedin-in") {
//                        href = "#"
//                        span("label") { +"""LinkedIn""" }
//                    }
//                }
            }
            ul("copyright") {
                li { +"""SPC""" }
                li {
                    +"""Design:"""
                    a {
                        href = "https://html5up.net"
                        +"""HTML5 UP"""
                    }
                }
            }
        }
    }
}

context(PageContext) private fun FlowContent.wrapper(contentBody: FlowContent.() -> Unit) {
    div {
        id = "wrapper"
        // Header
        header("alt") {
            id = "header"
            a(classes = "logo") {
                href = homeRef
                strong { +"""SPC""" }
                span { +"""Scientific Programming Centre""" }
            }
            nav {
                a {
                    href = "#menu"
                    +"""Menu"""
                }
            }
        }
        // Menu
        spcHomeMenu()
        //Body
        contentBody()
        // Footer
        spcFooter()
    }
}


context(PageContext) private fun HTML.spcPage(data: HtmlData) {
    val title = data.meta["title"].string ?: SPC_TITLE
    spcHead(title)
    body("is-preload") {
        wrapper {
            div("alt") {
                id = "main"
                // One
                section {
                    id = "one"
                    div("inner") {
                        header("major") {
                            h1 { +title }
                        }
                        data.meta["image"].string?.let { imagePath ->
                            span("image main") {
                                img {
                                    src = resolveRef(imagePath)
                                    alt = imagePath
                                }
                            }
                        }
                        htmlData(data)
                    }
                }
            }
        }

        fortyScripts()
    }
}

context(PageContext) private fun Route.spcPage(subRoute: String, data: HtmlData) {
    get(subRoute) {
        withRequest(call.request) {
            call.respondHtml {
                spcPage(data)
            }
        }
    }
}

context(PageContext) private fun Route.spcPage(subRoute: String, dataPath: String = subRoute) {
    val data = resolveHtml(dataPath.parseAsName())
    if (data != null) {
        spcPage(subRoute, data)
    } else {
        application.log.error("Content for page with path $dataPath not found")
    }

}

context(PageContext) private fun HTML.spcHome() {
    spcHead()
    body("is-preload") {
        wrapper {
            // Banner
            section("major") {
                id = "banner"
                div("inner") {
                    header("major") {
                        h1 { +"""Scientific Programming Centre""" }
                    }
                    div("content") {
                        p {
                            +"""Programming in Science"""
                            br {
                            }
                            +"""and Science in Programming"""
                        }
                        ul("actions") {
                            li {
                                a(classes = "button next scrolly") {
                                    href = "#master"
                                    +"""Activities"""
                                }
                            }
                        }
                    }
                }
            }
            // Main
            div {
                id = "main"
                // One
                section("tiles") {
                    id = "master"
                    article {
                        span("image") {
                            img {
                                src = resolveRef("images/pic01.jpg")
                                alt = ""
                            }
                        }
                        header("major") {
                            h3 {
                                a(classes = "link") {
                                    href = resolveRef("magprog")
                                    +"""Master program"""
                                }
                            }
                            p { +"""Master program: "Scientific programming" """ }
                        }
                    }
                    article {
                        span("image") {
                            img {
                                src = resolveRef("images/pic02.jpg")
                                alt = ""
                            }
                        }
                        header("major") {
                            h3 {
                                a(classes = "link") {
                                    href = resolveRef("research")
                                    +"""Research"""
                                }
                            }
                            p {
                                +"""Fundamental and applied research in analysis, scientific software design and data aquisition and control systems."""
                            }
                        }
                    }
                    article {
                        span("image") {
                            img {
                                src = resolveRef("images/pic03.jpg")
                                alt = ""
                            }
                        }
                        header("major") {
                            h3 {
                                a(classes = "link") {
                                    href = resolveRef("consulting")
                                    +"""Consulting"""
                                }
                            }
                            p { +"""Consultations, review and support of scientific software systems.""" }
                        }
                    }
                    article {
                        span("image") {
                            img {
                                src = resolveRef("images/pic04.jpg")
                                alt = ""
                            }
                        }
                        header("major") {
                            h3 {
                                a(classes = "link") {
                                    href = resolveRef("team")
                                    +"""Team"""
                                }
                            }
                            p { +"""Our team""" }
                        }
                    }
//                    article {
//                        span("image") {
//                            img {
//                                src = "images/pic05.jpg"
//                                alt = ""
//                            }
//                        }
//                        header("major") {
//                            h3 {
//                                a(classes = "link") {
//                                    href = "landing.html"
//                                    +"""Consequat"""
//                                }
//                            }
//                            p { +"""Ipsum dolor sit amet""" }
//                        }
//                    }
//                    article {
//                        span("image") {
//                            img {
//                                src = "images/pic06.jpg"
//                                alt = ""
//                            }
//                        }
//                        header("major") {
//                            h3 {
//                                a(classes = "link") {
//                                    href = "landing.html"
//                                    +"""Etiam"""
//                                }
//                            }
//                            p { +"""Feugiat amet tempus""" }
//                        }
//                    }
                }
                // Two
                section {
                    id = "two"
                    div("inner") {
                        header("major") {
                            h2 { +"""Science, education and industry working together""" }
                        }
                        p {
                            +"""
                                Our mission is to bring together science, education and industry and
                                work on better software solutions for science and better science in 
                                software development.
                            """.trimIndent()
                        }
//                        ul("actions") {
//                            li {
//                                a(classes = "button next") {
//                                    href = "landing.html"
//                                    +"""Get Started"""
//                                }
//                            }
//                        }
                    }
                }
            }
        }

        fortyScripts()
    }

}


internal fun Application.spcHome(context: Context, rootPath: Path, prefix: String = "") {

    val snark = context.fetch(SnarkPlugin)

    val homePageContext = snark.parse(prefix, rootPath.resolve("content"))

    routing {
        route(prefix) {
            with(homePageContext) {
                static("assets") {
                    files(rootPath.resolve("assets").toFile())
                }

                static("images") {
                    files(rootPath.resolve("images").toFile())
                }

                get {
                    withRequest(call.request) {
                        call.respondHtml {
                            spcHome()
                        }
                    }
                }

                spcPage("consulting")
                spcPage("research")
                spcPage("team")
            }
        }
    }
}