package ru.mipt.spc

import html5up.forty.fortyScripts
import kotlinx.html.*
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.meta.*
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.asName
import space.kscience.dataforge.names.startsWith
import space.kscience.snark.html.*
import java.nio.file.Path
import kotlin.reflect.typeOf


context(WebPage) internal fun HTML.spcPageContent(
    fragment: FlowContent.() -> Unit,
) {
    val title by pageMeta.string { SPC_TITLE }
    val pageName by pageMeta.string { title }
    spcHead(pageName)
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
                        pageMeta["image"]?.let { imageMeta ->
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
internal val FortyDataRenderer: DataRenderer = object : DataRenderer {

    context(SiteBuilder)
    override fun invoke(name: Name, data: Data<Any>) {
        if (data.type == typeOf<HtmlFragment>()) {
            data as Data<HtmlFragment>
            val languageMeta: Meta = Language.forName(name)

            val dataMeta: Meta = if (languageMeta.isEmpty()) {
                data.meta
            } else {
                data.meta.toMutableMeta().apply {
                    Language.LANGUAGES_KEY put languageMeta
                }
            }

            page(name, dataMeta) {
                spcPageContent{
                    htmlData(data)
                }
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
                        h1 {
                            +"""Scientific Programming Centre"""
                        }
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
                    div("inner home_creationinfo") {
                        a(href = "https://mipt.ru/education/departments/fpmi/") {
                            span("image") {
                                img {
                                    src = "images/FPMI.jpg"
                                    alt = "FPMI"
                                    height = "60dp"
                                    width = "60dp"
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

    site(prefix, homePageData) {
        file(dataPath.resolve("assets"))
        file(dataPath.resolve("images"))
        file(dataPath.resolve("../common"), "")

        withLanguages(
            "en" to "",
            "ru" to "ru"
        ) {
            page { spcHome() }

            localizedPages("consulting", dataRenderer = FortyDataRenderer)

            localizedPages("education", dataRenderer = FortyDataRenderer)

            spcSpotlight("team") { _, meta ->
                meta["type"].string == "team"
            }

            spcSpotlight("research") { name, meta ->
                name.startsWith("projects".asName()) && meta["type"].string == "project"
            }
        }
    }

}
