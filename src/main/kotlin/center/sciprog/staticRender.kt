package center.sciprog

import space.kscience.dataforge.context.Global
import space.kscience.dataforge.context.request
import space.kscience.snark.html.SiteBuilder
import space.kscience.snark.html.SnarkHtmlPlugin
import space.kscience.snark.html.readResourceDirectory
import space.kscience.snark.html.static
import java.nio.file.Path

fun main() {
    val snark = Global.request(SnarkHtmlPlugin)
    val siteData = snark.readResourceDirectory()

    snark.static(siteData, Path.of("build/public"), siteUrl = "", block = SiteBuilder::spcSite)
}