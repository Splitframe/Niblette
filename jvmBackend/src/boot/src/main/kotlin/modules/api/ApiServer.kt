package de.niblette.modules.api

import de.niblette.boot.Config
import de.niblette.common.BackgroundWorker
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

public class ApiServer(
    val config: Config,
    private val router: Routing.() -> Unit
) : BackgroundWorker {

    private val ktorServer = embeddedServer(Netty, port = 8080) {
//            install(DefaultHeaders)
//            install(CallLogging)
        routing {
            router()
        }
    }

    override fun start() {
        ktorServer.start(wait = true)
    }

    override fun close() {
        ktorServer.stop()
    }

}