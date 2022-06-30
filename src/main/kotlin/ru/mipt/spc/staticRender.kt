package ru.mipt.spc

import space.kscience.dataforge.context.Global
import space.kscience.dataforge.context.fetch
import space.kscience.snark.html.SnarkPlugin
import space.kscience.snark.html.renderStatic
import java.nio.file.Path
import kotlin.io.path.toPath

fun main() {
    Global.fetch(SnarkPlugin).renderStatic(Path.of("build/out")) {
        spcHome(rootPath = javaClass.getResource("/home")!!.toURI().toPath())
        spcMaster(dataPath = javaClass.getResource("/magprog")!!.toURI().toPath())
    }
}