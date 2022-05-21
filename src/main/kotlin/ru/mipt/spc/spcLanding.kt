package ru.mipt.spc

import html5up.forty.fortyScripts
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlinx.html.*
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.parseAsName
import space.kscience.dataforge.names.withIndex
import space.kscience.snark.*

context(PageContext) private fun FlowContent.spcTeamContent(
    landing: HtmlData,
    content: Map<Name, HtmlData>,
) {
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
                h1 { +(landing.meta["title"].string ?: "???") }
            }
            div("content") {
                htmlData(landing)
            }
        }
    }

    // Main
    div {
        id = "main"
        content.forEach { (name, data) ->
            val ref = resolveRef(name)

            section("spotlights") {
                id = data.meta["id"].string ?: name.toString()
                section {
                    data.meta["image"].string?.let { imagePath ->
                        a(classes = "image") {
                            href = ref
                            img {
                                src = resolveRef(imagePath)
                                alt = name.toString()
                                attributes["data-position"] = "center center"
                            }
                        }
                    }
                    div("content") {
                        div("inner") {
                            header("major") {
                                h3 { +(data.meta["title"].string ?: "???") }
                            }
                            resolveHtml(name.withIndex("info"))?.let {
                                htmlData(it)
                            }
                            ul("actions") {
                                li {
                                    a(classes = "button") {
                                        href = ref
                                        +"""Learn more"""
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


context(PageContext) internal fun Route.spcLanding(
    name: String,
    contentFilter: (Name, Meta) -> Boolean,
) {
    val body = resolveHtml(name.parseAsName()) ?: error("Could not find body for $name")
    val content = resolveAllHtml(contentFilter)

    val meta = body.meta
    get(name) {
        withRequest(call.request) {
            call.respondHtml {
                val title = meta["title"].string ?: SPC_TITLE
                spcHead(title)
                body("is-preload") {
                    wrapper {
                        spcTeamContent(body, content)
                    }

                    fortyScripts()
                }
            }
        }
    }
    content.forEach { (name, contentBody) ->
        get(name.tokens.joinToString("/")) {
            withRequest(call.request) {
                call.respondHtml {
                    spcPageContent(contentBody.meta) {
                        htmlData(contentBody)
                    }
                }
            }
        }
    }
}