package center.sciprog

import space.kscience.snark.SnarkEnvironment
import space.kscience.snark.html.static
import java.nio.file.Path
import kotlin.io.path.toPath

fun main() {
    SnarkEnvironment.default.static(Path.of("build/out")) {
        spcHome(dataPath = javaClass.getResource("/home")!!.toURI().toPath())
        spcMasters(dataPath = javaClass.getResource("/magprog")!!.toURI().toPath())
    }
}