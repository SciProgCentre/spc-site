package html5up.forty

import kotlinx.html.*
import space.kscience.snark.html.WebPage


internal fun FlowContent.fortyMenu() {
    nav {
        id = "menu"
        ul("links") {
            li {
                a {
                    href = "index.html"
                    +"""Home"""
                }
            }
            li {
                a {
                    href = "landing.html"
                    +"""Landing"""
                }
            }
            li {
                a {
                    href = "generic.html"
                    +"""Generic"""
                }
            }
            li {
                a {
                    href = "elements.html"
                    +"""Elements"""
                }
            }
        }
        ul("actions stacked") {
            li {
                a(classes = "button primary fit") {
                    href = "#"
                    +"""Get Started"""
                }
            }
            li {
                a(classes = "button fit") {
                    href = "#"
                    +"""Log In"""
                }
            }
        }
    }
}

internal fun FlowContent.fortyContactForm() {
    section {
        id = "contact"
        div("inner") {
            section {
                form {
                    method = FormMethod.post
                    action = "#"
                    div("fields") {
                        div("field half") {
                            label {
                                htmlFor = "name"
                                +"""Name"""
                            }
                            input {
                                type = InputType.text
                                name = "name"
                                id = "name"
                            }
                        }
                        div("field half") {
                            label {
                                htmlFor = "email"
                                +"""Email"""
                            }
                            input {
                                type = InputType.text
                                name = "email"
                                id = "email"
                            }
                        }
                        div("field") {
                            label {
                                htmlFor = "message"
                                +"""Message"""
                            }
                            textArea {
                                name = "message"
                                id = "message"
                                rows = "6"
                            }
                        }
                    }
                    ul("actions") {
                        li {
                            input(classes = "primary") {
                                type = InputType.submit
                                value = "Send Message"
                            }
                        }
                        li {
                            input {
                                type = InputType.reset
                                value = "Clear"
                            }
                        }
                    }
                }
            }
            section("split") {
                section {
                    div("contact-method") {
                        span("icon solid alt fa-envelope") {
                        }
                        h3 { +"""Email""" }
                        a {
                            href = "#"
                            +"""information@untitled.tld"""
                        }
                    }
                }
                section {
                    div("contact-method") {
                        span("icon solid alt fa-phone") {
                        }
                        h3 { +"""Phone""" }
                        span { +"""(000) 000-0000 x12387""" }
                    }
                }
                section {
                    div("contact-method") {
                        span("icon solid alt fa-home") {
                        }
                        h3 { +"""Address""" }
                        span {
                            +"""1234 Somewhere Road #5432"""
                            br {
                            }
                            +"""Nashville, TN 00000"""
                            br {
                            }
                            +"""United States of America"""
                        }
                    }
                }
            }
        }
    }
}

internal fun FlowContent.fortyFooter() {
    footer {
        id = "footer"
        div("inner") {
            ul("icons") {
                li {
                    a(classes = "icon brands alt fa-twitter") {
                        href = "#"
                        span("label") { +"""Twitter""" }
                    }
                }
                li {
                    a(classes = "icon brands alt fa-facebook-f") {
                        href = "#"
                        span("label") { +"""Facebook""" }
                    }
                }
                li {
                    a(classes = "icon brands alt fa-instagram") {
                        href = "#"
                        span("label") { +"""Instagram""" }
                    }
                }
                li {
                    a(classes = "icon brands alt fa-github") {
                        href = "#"
                        span("label") { +"""GitHub""" }
                    }
                }
                li {
                    a(classes = "icon brands alt fa-linkedin-in") {
                        href = "#"
                        span("label") { +"""LinkedIn""" }
                    }
                }
            }
            ul("copyright") {
                li { +"""&copy; Untitled""" }
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

context(WebPage) internal fun BODY.fortyScripts() {
    script {
        src = resolveRef("assets/js/jquery.min.js")
    }
    script {
        src = resolveRef("assets/js/jquery.scrolly.min.js")
    }
    script {
        src = resolveRef("assets/js/jquery.scrollex.min.js")
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