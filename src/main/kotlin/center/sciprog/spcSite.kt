package center.sciprog

import space.kscience.dataforge.data.*
import space.kscience.dataforge.misc.DFExperimental
import space.kscience.dataforge.names.Name
import space.kscience.snark.html.SiteBuilder

@OptIn(DFExperimental::class)
private fun <T : Any> DataSet<T>.siteData(branchName: String): DataTree<T> = DataTree(dataType) {
    populateFrom(branch(Name.of(branchName, "content")))
    node("common", branch("common"))
    node("assets", branch(Name.of(branchName, "assets")))
    node("images", branch(Name.of(branchName, "images")))
}

fun SiteBuilder.spcSite() {
//    val commonData = data.branch("common")
    spcHome(data.siteData("home"))
    spcMasters(data.siteData("magprog"))
//    bmk(data.branch("bmk").withBranch("common", commonData))
}