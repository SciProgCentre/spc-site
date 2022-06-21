package ru.mipt.spc

import html5up.forty.fortyScripts
import kotlinx.html.*
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.int
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.Name
import space.kscience.dataforge.names.parseAsName
import space.kscience.dataforge.names.withIndex
import space.kscience.dataforge.values.string
import space.kscience.snark.*
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

context(SiteContext) private fun FlowContent.spcSpotlightContent(
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
        //TODO add smart SNARK ordering
        section("spotlights") {
            content.entries.sortedBy { it.value.meta["order"].int ?: Int.MAX_VALUE }.forEach { (name, data) ->
                val ref = resolveRef(name)
                section {
                    id = data.meta["id"].string ?: name.toString()
                    data.meta["image"]?.let { imageMeta: Meta ->
                        val imagePath =
                            imageMeta.value?.string ?: imageMeta["path"].string ?: error("Image path not provided")
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
                            val infoData = resolveHtml(name.withIndex("info"))
                            if (infoData == null) {
                                htmlData(data)
                            } else {
                                htmlData(infoData)
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


context(SiteContext) internal fun SiteBuilder.spcSpotlight(
    name: String,
    contentFilter: (Name, Meta) -> Boolean,
) {
    val body = resolveHtml(name.parseAsName()) ?: error("Could not find body for $name")
    val content = resolveAllHtml(contentFilter)

    val meta = body.meta
    page(name) {
        val title = meta["title"].string ?: SPC_TITLE
        spcHead(title)
        body("is-preload") {
            wrapper {
                spcSpotlightContent(body, content)
            }

            fortyScripts()
        }
    }

    content.forEach { (name, contentBody) ->
        page(name.tokens.joinToString("/")){
            spcPageContent(contentBody.meta) {
                htmlData(contentBody)
            }
        }
    }
}