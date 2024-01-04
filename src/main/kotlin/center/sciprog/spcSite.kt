package center.sciprog

import space.kscience.dataforge.data.*
import space.kscience.dataforge.misc.DFExperimental
import space.kscience.dataforge.names.Name
import space.kscience.snark.html.HtmlSite
import space.kscience.snark.html.site

@OptIn(DFExperimental::class)
private fun <T : Any> DataSet<T>.contentFor(branchName: String): DataTree<T> = DataTree(dataType) {
    populateFrom(branch(Name.of(branchName, "content")))
    node("common", branch("common"))
    node("assets", branch(Name.of(branchName, "assets")))
    node("images", branch(Name.of(branchName, "images")))
}

val spcSite = HtmlSite {
    route(Name.EMPTY, siteData.contentFor("home"), content = spcHome)
    site("education.masters", siteData.contentFor("magprog"), content = spcMasters)
//    bmk(data.branch("bmk").withBranch("common", commonData))
}
