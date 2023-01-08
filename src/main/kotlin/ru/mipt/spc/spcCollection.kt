package ru.mipt.spc

import html5up.forty.fortyScripts
import kotlinx.html.*
import space.kscience.dataforge.data.Data
import space.kscience.dataforge.meta.Meta
import space.kscience.dataforge.meta.get
import space.kscience.dataforge.meta.int
import space.kscience.dataforge.meta.string
import space.kscience.dataforge.names.*
import space.kscience.snark.html.*
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

context(WebPage) private fun FlowContent.spcSpotlightContent(
    landing: HtmlData,
    content: Map<Name, HtmlData>,
) {
    // Banner
    // Note: The "styleN" class below should match that of the header element.
    section("style1") {
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
            content.entries.sortedBy { it.value.meta["order"].int ?: Int.MAX_VALUE }.forEach { (name, entry) ->
                val ref = resolvePageRef(name)
                section {
                    id = entry.meta["id"].string ?: name.toString()
                    entry.meta["image"]?.let { imageMeta: Meta ->
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
                                h3 { +(entry.meta["title"].string ?: "???") }
                            }
                            val infoData = data.resolveHtml(name.withIndex("info"))
                            if (infoData == null) {
                                htmlData(entry)
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


internal fun SiteBuilder.spcSpotlight(
    address: String,
    contentFilter: (Name, Meta) -> Boolean,
) {
    val pageName = address.parseAsName()
    val languagePrefix = languagePrefix
    val body = data.resolveHtml(languagePrefix + pageName)
        ?: data.resolveHtml(pageName) ?: error("Could not find body for $pageName")
    val content: Map<Name, Data<HtmlFragment>> =
        data.resolveAllHtml { name, meta -> name.startsWith(languagePrefix) && contentFilter(name, meta) }

    val meta = body.meta
    page(pageName) {
        val title by meta.string { SPC_TITLE }
        spcHead(title)
        body("is-preload") {
            wrapper {
                spcSpotlightContent(body, content)
            }

            fortyScripts()
        }
    }

    content.forEach { (name, contentBody) ->
        page(name, contentBody.meta) {
            spcPageContent {
                htmlData(contentBody)
            }
        }
    }
}