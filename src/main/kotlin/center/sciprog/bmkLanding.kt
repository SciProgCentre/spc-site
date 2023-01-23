package center.sciprog

import kotlinx.coroutines.runBlocking
import kotlinx.html.*
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.data.DataTree
import space.kscience.dataforge.data.await
import space.kscience.dataforge.data.getByType
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.getIndexed
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.parseAsName
import space.kscience.snark.html.*
import java.nio.file.Path


private val Data<*>.title: String
    get() = meta["title"].string ?: ""

private val Data<*>.fragment: String
    get() = meta["fragment"].string ?: ""


internal fun SiteBuilder.bmk(dataPath: Path, prefix: Name = "bmk".parseAsName()) {

    val data: DataTree<Any> = snark.readDirectory(dataPath.resolve("content"))

    site(prefix, data) {
        file(dataPath.resolve("assets"))
        file(dataPath.resolve("images"))
        file(dataPath.resolve("../common"), "")

        val about: Data<HtmlFragment> = data.resolveHtml("about")!!
        val team: Data<HtmlFragment> = data.resolveHtml("team.index")!!
        val teamData: Map<Name, Data<HtmlFragment>> = data.resolveAllHtml { _, meta -> meta["type"].string == "team" }
        val solutions: Data<HtmlFragment> = data.resolveHtml("lotSeis")!!
        val partners: Data<HtmlFragment> = data.resolveHtml("partners")!!
        val partnersData = runBlocking { data.getByType<Meta>("partnersData")!!.await() }

        page {
            head {
                title = "БМК-Сервис"
                meta {
                    charset = "utf-8"
                }
                meta {
                    name = "viewport"
                    content = "width=device-width, initial-scale=1, user-scalable=no"
                }
                link {
                    rel = "stylesheet"
                    href = resolveRef("assets/css/main.css")
                }
                noScript {
                    link {
                        rel = "stylesheet"
                        href = resolveRef("assets/css/noscript.css")
                    }
                }
            }
            body("is-preload") {
//              Wrapper
                div {
                    id = "wrapper"
//                  Header
                    header("alt") {
                        id = "header"
                        span("logo") {
                            img {
                                src = "images/logo.svg"
                                alt = ""
                            }
                        }
                        h1 { +"""БМК-Сервис""" }
//                        p {
//                            +"""Just another free, fully responsive site template"""
//                            br {
//                            }
//                            +"""built by"""
//                            a {
//                                href = "https://twitter.com/ajlkn"
//                                +"""@ajlkn"""
//                            }
//                            +"""for"""
//                            a {
//                                href = "https://html5up.net"
//                                +"""HTML5 UP"""
//                            }
//                            +"""."""
//                        }
                    }
//                  Nav
                    nav {
                        id = "nav"
                        ul {
                            li {
                                a(classes = "active") {
                                    href = "#${about.fragment}"
                                    +about.title
                                }
                            }
                            li {
                                a {
                                    href = "#${team.fragment}"
                                    +team.title
                                }
                            }
                            li {
                                a {
                                    href = "#${solutions.fragment}"
                                    +solutions.title
                                }
                            }
                            li {
                                a {
                                    href = "#${partners.fragment}"
                                    +partners.title
                                }
                            }
                        }
                    }
                    div {
                        id = "main"
                        section("main") {
                            id = about.fragment
                            div("spotlight") {
                                div("content") {
                                    header("major") {
                                        h2 { +about.title }
                                    }
                                    htmlData(about)
                                }
                            }
                        }
                        section("main") {
                            id = team.fragment
                            header("major") {
                                h2 { +team.title }
                            }
                            htmlData(team)
                            teamData.values.sortedBy { it.order }.forEach { data ->
                                span("image left") {
                                    img {
                                        src = resolveRef("images/${data.meta["image"].string!!}")
                                        height = "120dp"
                                    }
                                }
                                h3 { +data.title }
                                htmlData(data)
                            }
                        }
                        section("main") {
                            id = solutions.fragment
                            header("major") {
                                h2 { +solutions.title }
                                htmlData(solutions)
                                span("image fit") {
                                    img {
                                        src = resolveRef("images/fresnel_lands_critdepth2.png")
                                    }
                                }
                            }
                        }
                        section("main") {
                            id = partners.fragment
                            header("major") {
                                h2 { +partners.title }
                                htmlData(partners)
                                table {
                                    partnersData.getIndexed("content").values.forEach {
                                        tr {
                                            td {
                                                span("image right") {
                                                    img {
                                                        src = resolveRef(it["image"].string!!)
                                                        height = "120dp"
                                                    }
                                                }
                                                h3 {
                                                    a(href = it["target"].string!!) {
                                                        +it["title"].string!!
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
//                  Footer
                    footer {
//                        id = "footer"
//                        section {
//                            h2 { +"""Aliquam sed mauris""" }
//                            p { +"""Sed lorem ipsum dolor sit amet et nullam consequat feugiat consequat magna adipiscing tempus etiam dolore veroeros. eget dapibus mauris. Cras aliquet, nisl ut viverra sollicitudin, ligula erat egestas velit, vitae tincidunt odio.""" }
//                            ul("actions") {
//                                li {
//                                    a(classes = "button") {
//                                        href = "generic.html"
//                                        +"""Learn More"""
//                                    }
//                                }
//                            }
//                        }
//                        section {
//                            h2 { +"""Etiam feugiat""" }
//                            dl("alt") {
//                                dt { +"""Address""" }
//                                dd { +"""1234 Somewhere Road &bull; Nashville, TN 00000 &bull; USA""" }
//                                dt { +"""Phone""" }
//                                dd { +"""(000) 000-0000 x 0000""" }
//                                dt { +"""Email""" }
//                                dd {
//                                    a {
//                                        href = "#"
//                                        +"""information@untitled.tld"""
//                                    }
//                                }
//                            }
//                            ul("icons") {
//                                li {
//                                    a(classes = "icon brands fa-twitter alt") {
//                                        href = "#"
//                                        span("label") { +"""Twitter""" }
//                                    }
//                                }
//                                li {
//                                    a(classes = "icon brands fa-facebook-f alt") {
//                                        href = "#"
//                                        span("label") { +"""Facebook""" }
//                                    }
//                                }
//                                li {
//                                    a(classes = "icon brands fa-instagram alt") {
//                                        href = "#"
//                                        span("label") { +"""Instagram""" }
//                                    }
//                                }
//                                li {
//                                    a(classes = "icon brands fa-github alt") {
//                                        href = "#"
//                                        span("label") { +"""GitHub""" }
//                                    }
//                                }
//                                li {
//                                    a(classes = "icon brands fa-dribbble alt") {
//                                        href = "#"
//                                        span("label") { +"""Dribbble""" }
//                                    }
//                                }
//                            }
//                        }
                        p("copyright") {
                            +"""SPC. Design:"""
                            a {
                                href = "https://html5up.net"
                                +"""HTML5 UP"""
                            }
                            +"""."""
                        }
                    }
                }
//              Scripts
                script {
                    src = resolveRef("assets/js/jquery.min.js")
                }
                script {
                    src = resolveRef("assets/js/jquery.scrollex.min.js")
                }
                script {
                    src = resolveRef("assets/js/jquery.scrolly.min.js")
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
        }
    }
}
