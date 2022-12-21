package ru.mipt.spc

import kotlinx.html.*
import space.kscience.snark.html.WebPage
import space.kscience.snark.html.homeRef
import space.kscience.snark.html.resolvePageRef
import java.time.LocalDate


internal const val SPC_TITLE = "Scientific Programming Centre"

context(WebPage) internal fun HTML.spcHead(title: String = SPC_TITLE) {
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

context(WebPage) internal fun FlowContent.spcHomeMenu() {
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
                    href = resolvePageRef("education.index")
                    +"""Education"""
                }
            }
            li {
                a {
                    href = resolvePageRef("research")
                    +"""Research"""
                }
            }
            li {
                a {
                    href = resolvePageRef("consulting.index")
                    +"""Consulting"""
                }
            }
            li {
                a {
                    href = resolvePageRef("team")
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

context(WebPage) internal fun FlowContent.spcFooter() {
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
                        href = "https://github.com/SciProgCentre"
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
                li { +"""SPC. All rights reserved.""" }
                li { +"Updated on ${LocalDate.now()}"}
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

context(WebPage) internal fun FlowContent.wrapper(contentBody: FlowContent.() -> Unit) {
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
