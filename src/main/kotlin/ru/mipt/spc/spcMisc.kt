package ru.mipt.spc

import kotlinx.html.*
import space.kscience.snark.html.Page
import space.kscience.snark.html.homeRef
import space.kscience.snark.html.resolvePageRef


internal const val SPC_TITLE = "Scientific Programming Centre"

context(Page) internal fun HTML.spcHead(title: String = SPC_TITLE) {
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

context(Page) internal fun FlowContent.spcHomeMenu() {
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
                    href = resolvePageRef("magprog")
                    +"""Master"""
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
                    href = resolvePageRef("consulting")
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

context(Page) internal fun FlowContent.spcFooter() {
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

context(Page) internal fun FlowContent.wrapper(contentBody: FlowContent.() -> Unit) {
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