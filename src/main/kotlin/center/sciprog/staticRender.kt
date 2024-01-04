package center.sciprog

import kotlinx.coroutines.coroutineScope
import space.kscience.dataforge.context.Context
import space.kscience.dataforge.context.request
import space.kscience.snark.html.*
import space.kscience.snark.html.static.staticSite
import java.nio.file.Path

suspend fun main(args: Array<String>)  = coroutineScope{
    val context = Context{
        plugin(SnarkHtml)
    }

    val destinationPath = args.firstOrNull() ?: "build/public"

    val snark = context.request(SnarkHtml)
    val siteData = snark.readResources("common", "home", "magprog")

    snark.staticSite(siteData, Path.of(destinationPath), content = spcSite)
}