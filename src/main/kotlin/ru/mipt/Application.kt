package ru.mipt

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import ru.mipt.plugins.configureRouting
import ru.mipt.plugins.configureTemplating

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureTemplating()
    }.start(wait = true)
}
