package html5up.forty

import kotlinx.html.*
import space.kscience.snark.SiteContext
import space.kscience.snark.resolveRef

context(SiteContext) internal fun HTML.fortyPage(){
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
            href = resolveRef(resolveRef("assets/css/main.css"))
        }
        noScript {
            link {
                rel = "stylesheet"
                href = resolveRef(resolveRef("assets/css/noscript.css"))
            }
        }
    }
    body("is-preload") {
        // Wrapper
        div {
            id = "wrapper"
            // Header
            header {
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
            // Main
            div("alt") {
                id = "main"
                // One
                section {
                    id = "one"
                    div("inner") {
                        header("major") {
                            h1 { +"""Generic""" }
                        }
                        span("image main") {
                            img {
                                src = "images/pic11.jpg"
                                alt = ""
                            }
                        }
                        p { +"""Donec eget ex magna. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque venenatis dolor imperdiet dolor mattis sagittis. Praesent rutrum sem diam, vitae egestas enim auctor sit amet. Pellentesque leo mauris, consectetur id ipsum sit amet, fergiat. Pellentesque in mi eu massa lacinia malesuada et a elit. Donec urna ex, lacinia in purus ac, pretium pulvinar mauris. Curabitur sapien risus, commodo eget turpis at, elementum convallis elit. Pellentesque enim turpis, hendrerit.""" }
                        p { +"""Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis dapibus rutrum facilisis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Etiam tristique libero eu nibh porttitor fermentum. Nullam venenatis erat id vehicula viverra. Nunc ultrices eros ut ultricies condimentum. Mauris risus lacus, blandit sit amet venenatis non, bibendum vitae dolor. Nunc lorem mauris, fringilla in aliquam at, euismod in lectus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In non lorem sit amet elit placerat maximus. Pellentesque aliquam maximus risus, vel sed vehicula.""" }
                        p { +"""Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque venenatis dolor imperdiet dolor mattis sagittis. Praesent rutrum sem diam, vitae egestas enim auctor sit amet. Pellentesque leo mauris, consectetur id ipsum sit amet, fersapien risus, commodo eget turpis at, elementum convallis elit. Pellentesque enim turpis, hendrerit tristique lorem ipsum dolor.""" }
                    }
                }
            }
            fortyContactForm()
            fortyFooter()
        }
        fortyScripts()
    }
}