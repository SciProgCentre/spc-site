package html5up.forty

import kotlinx.html.*
import space.kscience.snark.SiteContext

context(SiteContext) internal fun HTML.landing(){
    head {
        title {
        }
        meta {
            charset = "utf-8"
        }
        meta {
            name = "viewport"
            content = "width=device-width, initial-scale=1, user-scalable=no"
        }
        link {
            rel = "stylesheet"
            href = "assets/css/main.css"
        }
        noScript {
            link {
                rel = "stylesheet"
                href = "assets/css/noscript.css"
            }
        }
    }
    body("is-preload") {
        // Wrapper
        div {
            id = "wrapper"
            // Header
			// Note: The "styleN" class below should match that of the banner element. -->

            header("alt style2") {
                id = "header"
                a(classes = "logo") {
                    href = "index.html"
                    strong { +"""Forty""" }
                    span { +"""by HTML5 UP""" }
                }
                nav {
                    a {
                        href = "#menu"
                        +"""Menu"""
                    }
                }
            }
            fortyMenu()
            // Banner
		    // Note: The "styleN" class below should match that of the header element.
            section("style2") {
                id = "banner"
                div("inner") {
                    span("image") {
                        img {
                            src = "images/pic07.jpg"
                            alt = ""
                        }
                    }
                    header("major") {
                        h1 { +"""Landing""" }
                    }
                    div("content") {
                        p {
                            +"""Lorem ipsum dolor sit amet nullam consequat"""
                            br {
                            }
                            +"""sed veroeros. tempus adipiscing nulla."""
                        }
                    }
                }
            }
            // Main
            div {
                id = "main"
                // One"""
                section {
                    id = "one"
                    div("inner") {
                        header("major") {
                            h2 { +"""Sed amet aliquam""" }
                        }
                        p { +"""Nullam et orci eu lorem consequat tincidunt vivamus et sagittis magna sed nunc rhoncus condimentum sem. In efficitur ligula tate urna. Maecenas massa vel lacinia pellentesque lorem ipsum dolor. Nullam et orci eu lorem consequat tincidunt. Vivamus et sagittis libero. Nullam et orci eu lorem consequat tincidunt vivamus et sagittis magna sed nunc rhoncus condimentum sem. In efficitur ligula tate urna.""" }
                    }
                }
                // Two"""
                section("spotlights") {
                    id = "two"
                    section {
                        a(classes = "image") {
                            href = "generic.html"
                            img {
                                src = "images/pic08.jpg"
                                alt = ""
                                attributes["data-position"] = "center center"
                            }
                        }
                        div("content") {
                            div("inner") {
                                header("major") {
                                    h3 { +"""Orci maecenas""" }
                                }
                                p { +"""Nullam et orci eu lorem consequat tincidunt vivamus et sagittis magna sed nunc rhoncus condimentum sem. In efficitur ligula tate urna. Maecenas massa sed magna lacinia magna pellentesque lorem ipsum dolor. Nullam et orci eu lorem consequat tincidunt. Vivamus et sagittis tempus.""" }
                                ul("actions") {
                                    li {
                                        a(classes = "button") {
                                            href = "generic.html"
                                            +"""Learn more"""
                                        }
                                    }
                                }
                            }
                        }
                    }
                    section {
                        a(classes = "image") {
                            href = "generic.html"
                            img {
                                src = "images/pic09.jpg"
                                alt = ""
                                attributes["data-position"] = "top center"
                            }
                        }
                        div("content") {
                            div("inner") {
                                header("major") {
                                    h3 { +"""Rhoncus magna""" }
                                }
                                p { +"""Nullam et orci eu lorem consequat tincidunt vivamus et sagittis magna sed nunc rhoncus condimentum sem. In efficitur ligula tate urna. Maecenas massa sed magna lacinia magna pellentesque lorem ipsum dolor. Nullam et orci eu lorem consequat tincidunt. Vivamus et sagittis tempus.""" }
                                ul("actions") {
                                    li {
                                        a(classes = "button") {
                                            href = "generic.html"
                                            +"""Learn more"""
                                        }
                                    }
                                }
                            }
                        }
                    }
                    section {
                        a(classes = "image") {
                            href = "generic.html"
                            img {
                                src = "images/pic10.jpg"
                                alt = ""
                                attributes["data-position"] = "25% 25%"
                            }
                        }
                        div("content") {
                            div("inner") {
                                header("major") {
                                    h3 { +"""Sed nunc ligula""" }
                                }
                                p { +"""Nullam et orci eu lorem consequat tincidunt vivamus et sagittis magna sed nunc rhoncus condimentum sem. In efficitur ligula tate urna. Maecenas massa sed magna lacinia magna pellentesque lorem ipsum dolor. Nullam et orci eu lorem consequat tincidunt. Vivamus et sagittis tempus.""" }
                                ul("actions") {
                                    li {
                                        a(classes = "button") {
                                            href = "generic.html"
                                            +"""Learn more"""
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // Three
                section {
                    id = "three"
                    div("inner") {
                        header("major") {
                            h2 { +"""Massa libero""" }
                        }
                        p { +"""Nullam et orci eu lorem consequat tincidunt vivamus et sagittis libero. Mauris aliquet magna magna sed nunc rhoncus pharetra. Pellentesque condimentum sem. In efficitur ligula tate urna. Maecenas laoreet massa vel lacinia pellentesque lorem ipsum dolor. Nullam et orci eu lorem consequat tincidunt. Vivamus et sagittis libero. Mauris aliquet magna magna sed nunc rhoncus amet pharetra et feugiat tempus.""" }
                        ul("actions") {
                            li {
                                a(classes = "button next") {
                                    href = "generic.html"
                                    +"""Get Started"""
                                }
                            }
                        }
                    }
                }
            }

            fortyContactForm()

            fortyFooter()
        }
        // Scripts
        fortyScripts()
    }
}