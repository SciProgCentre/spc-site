package ru.mipt.spc

import html5up.forty.fortyScripts
import kotlinx.html.*
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.asName
import space.kscience.dataforge.names.startsWith
import space.kscience.snark.html.*
import java.nio.file.Path
import kotlin.reflect.typeOf


context(WebPage) internal fun HTML.spcPageContent(
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

@Suppress("UNCHECKED_CAST")
internal val FortyDataRenderer: DataRenderer = { name, data ->
    if (data.type == typeOf<HtmlFragment>()) {
        data as Data<HtmlFragment>
        page(name) {
            spcPageContent(data.meta) {
                htmlData(data)
            }
        }
    }
}


context(WebPage) private fun HTML.spcHome() {
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
                            br {}
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
                section {
                    div("inner") {
                        a(href = "https://mipt.ru/education/departments/fpmi/") {
                            span("image left") {
                                img {
                                    src = "images/FPMI.jpg"
                                    alt = "FPMI"
                                    height = "60"
                                    width = "60"
                                }
                            }
                        }
                        p {
                            +"Centre was created in 2022 based on the Phystech School of Applied Mathematics and Informatics at MIPT"
                        }
                    }
                }

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
                                    href = resolvePageRef("education")
                                    +"""Education"""
                                }
                            }
                            p { +""" Educational projects""" }
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
                                    href = resolvePageRef("research")
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
                                    href = resolvePageRef("consulting.index")
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
                                    href = resolvePageRef("team")
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
            }
        }

        fortyScripts()
    }

}

internal fun SiteBuilder.spcHome(dataPath: Path, prefix: Name = Name.EMPTY) {

    val homePageData = snark.readDirectory(dataPath.resolve("content"))

    route(prefix, homePageData, setAsRoot = true) {
        file(dataPath.resolve("assets"))
        file(dataPath.resolve("images"))
        file(dataPath.resolve("../common"), "")

        page { spcHome() }

        pages("consulting", dataRenderer = FortyDataRenderer)
        //pages("ru.consulting".parseAsName(), dataRenderer = FortyDataRenderer)

        pages("education", dataRenderer = FortyDataRenderer)

        spcSpotlight("team") { _, meta ->
            meta["type"].string == "team"
        }

        spcSpotlight("research") { name, meta ->
            name.startsWith("projects".asName()) && meta["type"].string == "project"
        }
    }

}