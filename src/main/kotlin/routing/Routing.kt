package routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("The most dangerous adversary is the one you underestimate.")
        }
    }
}

