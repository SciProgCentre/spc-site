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
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.parseAsName
import space.kscience.dataforge.values.string
import space.kscience.snark.*
import java.nio.file.Path


context(PageContext) internal fun HTML.spcPageContent(
    meta: Meta,
    title: String = meta["title"].string ?: SPC_TITLE,
    fragment: FlowContent.() -> Unit,
) {
    spcHead(title)
    body("is-preload") {
        wrapper {
            div("alt") {
                id = "main"
                // One
                section {
                    div("inner") {
                        header("major") {
                            h1 { +title }
                        }
                        meta["image"]?.let { imageMeta ->
                            val imagePath =
                                imageMeta.value?.string ?: imageMeta["path"].string ?: error("Image path not provided")
                            val imageClass = imageMeta["position"].string ?: "main"
                            span("image $imageClass") {
                                img {
                                    src = resolveRef(imagePath)
                                    alt = imagePath
                                }
                            }
                        }
                        fragment()
                    }
                }
            }
        }

        fortyScripts()
    }
}


context(PageContext) internal fun Route.spcPage(subRoute: String, meta: Meta, fragment: FlowContent.() -> Unit) {
    get(subRoute) {
        withRequest(call.request) {
            call.respondHtml {
                spcPageContent(meta, fragment = fragment)
            }
        }
    }
}

context(PageContext) internal fun Route.spcPage(
    subRoute: String,
    dataPath: Name = subRoute.parseAsName(),
    more: FlowContent.() -> Unit = {},
) {
    val data = resolveHtml(dataPath)
    if (data != null) {
        spcPage(subRoute, data.meta) {
            htmlData(data)
            more()
        }
    } else {
        application.log.error("Content for page with path $dataPath not found")
    }

}

context(PageContext) internal fun Route.spcPage(
    name: Name,
    more: FlowContent.() -> Unit = {},
) {
    spcPage(name.tokens.joinToString("/"), name, more)
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
                            +"Programming in Science"
                            br{}
                            entity(Entities.nbsp)
                            +"and Science in Programming"
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
                                +"""Fundamental and applied research in analysis, scientific software design and data acquisition and control systems."""
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
                    div("inner") {
                        header("major") {
                            h2 { +"Science + education + industry" }
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

                spcSpotlight("team") { _, m -> m["type"].string == "team" }
                spcSpotlight("research") { _, m -> m["type"].string == "project" }
            }
        }
    }
}