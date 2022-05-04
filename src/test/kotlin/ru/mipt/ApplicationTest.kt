package ru.mipt

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import ru.mipt.spc.magprog.magProgPage
import space.kscience.dataforge.context.Context
import space.kscience.snark.SnarkPlugin
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        val context = Context("spc-site") {
            plugin(SnarkPlugin)
        }

        application {
            magProgPage(context, rootPath = Path.of(javaClass.getResource("/magprog")!!.toURI()))
        }
        client.get("/magprog").apply {
            assertEquals(HttpStatusCode.OK, status)
//            assertEquals("Hello World!", bodyAsText())
        }
    }
}