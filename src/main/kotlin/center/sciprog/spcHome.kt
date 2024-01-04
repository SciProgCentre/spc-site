package center.sciprog

import html5up.forty.fortyScripts
import kotlinx.html.*
import space.kscience.dataforge.data.*
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.asName
import space.kscience.dataforge.names.startsWith
import space.kscience.snark.html.*


internal fun spcPage(content: Data<PageFragment>) = HtmlPage {
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
                                imageMeta.value?.string ?: imageMeta["path"].string
                                ?: error("Image path not provided")
                            val imageClass = imageMeta["position"].string ?: "main"
                            span("image $imageClass") {
                                img {
                                    src = resolveRef(imagePath)
                                    alt = imagePath
                                }
                            }
                        }
                        fragment(content)
                    }
                }
            }
        }

        fortyScripts()
    }
}

internal val spcHomePage = HtmlPage {
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
//                section {
//                    div("inner home_creationinfo") {
//                        a(href = "https://mipt.ru/education/departments/fpmi/") {
//                            span("image") {
//                                img {
//                                    src = "images/FPMI.jpg"
//                                    alt = "FPMI"
//                                    height = "60dp"
//                                    width = "60dp"
//                                }
//                            }
//                        }
//                        p {
//                            +"Centre was created in 2022 based on the Phystech School of Applied Mathematics and Informatics at MIPT"
//                        }
//                    }
//                }

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
                                src = page.resolveRef("images/pic01.jpg")
                                alt = ""
                            }
                        }
                        header("major") {
                            h3 {
                                a(classes = "link") {
                                    href = page.resolvePageRef("education")
                                    +"""Education"""
                                }
                            }
                            p { +""" Educational projects""" }
                        }
                    }
                    article {
                        span("image") {
                            img {
                                src = page.resolveRef("images/pic02.jpg")
                                alt = ""
                            }
                        }
                        header("major") {
                            h3 {
                                a(classes = "link") {
                                    href = page.resolvePageRef("research")
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
                                src = page.resolveRef("images/pic03.jpg")
                                alt = ""
                            }
                        }
                        header("major") {
                            h3 {
                                a(classes = "link") {
                                    href = page.resolvePageRef("consulting.index")
                                    +"""Consulting"""
                                }
                            }
                            p { +"""Consultations, review and support of scientific software systems.""" }
                        }
                    }
                    article {
                        span("image") {
                            img {
                                src = page.resolveRef("images/pic04.jpg")
                                alt = ""
                            }
                        }
                        header("major") {
                            h3 {
                                a(classes = "link") {
                                    href = page.resolvePageRef("team")
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

private fun SiteContextWithData.allPagesIn(location: String){
    siteData.filterByType<PageFragment> { name, meta ->
        name.startsWith(location) && meta["type"].string == "page"
    }.forEach { (name, content) ->
        page(name, content = spcPage(content))
    }
}


internal val spcHome: HtmlSite = HtmlSite {
    static("assets")
    static("images")
    static("common", "")

    multiLanguageSite(
        siteData,
        mapOf(
            "en" to Language(""),
            "ru" to Language("ru"),
        )
    ) {
        page(content = spcHomePage)

        allPagesIn("consulting")

        allPagesIn("education")

        spcSpotlight("team") { _, meta ->
            meta["type"].string == "team"
        }

        spcSpotlight("research") { name, meta ->
            name.startsWith("projects".asName()) && meta["type"].string == "project"
        }
    }
//    withLanguages(
//        "en" to "",
//        "ru" to "ru"
//    ) {
//        page { spcHomePage() }
//
//        localizedPages("consulting", dataRenderer = FortyDataRenderer)
//
//        localizedPages("education", dataRenderer = FortyDataRenderer)
//
//        spcSpotlight("team") { _, meta ->
//            meta["type"].string == "team"
//        }
//
//        spcSpotlight("research") { name, meta ->
//            name.startsWith("projects".asName()) && meta["type"].string == "project"
//        }
//    }

}
//
//internal fun SiteBuilder.spcHome(homePageData: DataTree<Any>, prefix: Name = Name.EMPTY) {
//
//    //val homePageData: DataTree<Any> = snark.readDirectory(dataPath.resolve("content"))
//
//    site(prefix, homePageData) {
//        static("assets")
//        static("images")
//        static("common", "")
//
//        withLanguages(
//            "en" to "",
//            "ru" to "ru"
//        ) {
//            page { spcHomePage() }
//
//            localizedPages("consulting", dataRenderer = FortyDataRenderer)
//
//            localizedPages("education", dataRenderer = FortyDataRenderer)
//
//            spcSpotlight("team") { _, meta ->
//                meta["type"].string == "team"
//            }
//
//            spcSpotlight("research") { name, meta ->
//                name.startsWith("projects".asName()) && meta["type"].string == "project"
//            }
//        }
//    }
//}
