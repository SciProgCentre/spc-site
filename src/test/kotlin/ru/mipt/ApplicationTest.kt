package ru.mipt

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            magProgPage(rootPath = Path.of(javaClass.getResource("/magprog")!!.toURI()))
        }
        client.get("/magprog").apply {
            assertEquals(HttpStatusCode.OK, status)
//            assertEquals("Hello World!", bodyAsText())
        }
    }
}