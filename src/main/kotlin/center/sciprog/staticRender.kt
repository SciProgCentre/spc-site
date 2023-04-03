package center.sciprog

import space.kscience.dataforge.context.Global
import space.kscience.dataforge.context.request
import space.kscience.snark.html.SiteBuilder
import space.kscience.snark.html.SnarkHtmlPlugin
import space.kscience.snark.html.readResources
import space.kscience.snark.html.static
import java.nio.file.Path

fun main(args: Array<String>) {
    val destinationPath = args.firstOrNull() ?: "build/public"

    val snark = Global.request(SnarkHtmlPlugin)
    val siteData = snark.readResources("common", "home", "magprog")

    snark.static(siteData, Path.of(destinationPath), block = SiteBuilder::spcSite)
}