package de.niblette.boot.api

import de.niblette.boot.Config
import de.niblette.common.BackgroundWorker
import de.niblette.common.Module
import de.niblette.modules.api.ApiServer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

interface ApiModule : Module {

}


class ApiModuleImpl(
    private val config: Config,
) : ApiModule {

    private val router: Routing.() -> Unit = {
        route("/info") {
            get() {
                call.respond(HttpStatusCode.OK)
            }
        }
    }

    private val server: ApiServer = ApiServer(
        config = config,
        router = router
    )

    override val backgroundWorker: List<BackgroundWorker> = listOf(
        server
    )
}