package ru.mipt.spc

import space.kscience.dataforge.context.Global
import space.kscience.dataforge.context.fetch
import space.kscience.snark.SnarkPlugin
import space.kscience.snark.static
import java.nio.file.Path
import kotlin.io.path.toPath

fun main() {
    Global.fetch(SnarkPlugin).static(Path.of("build/out")) {
        spcHome(rootPath = javaClass.getResource("/home")!!.toURI().toPath())
        spcMaster(dataPath = javaClass.getResource("/magprog")!!.toURI().toPath())
    }
}