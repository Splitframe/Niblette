package de.splitframe.application

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

//fun HTML.index() {
//    head {
//        title("Hello from Ktor!")
//    }
//    body {
//        div {
//            +"Hello from Ktor"
//        }
//        div {
//            id = "root"
//        }
//        script(src = "/static/Niblette.js") {}
//    }
//}

fun main(args: Array<String>) = EngineMain.main(args)

@Suppress("unused")
fun Application.entry() {
    routing {
        get("/") {
//            call.respondHtml(HttpStatusCode.OK, HTML::index)
            call.respondText("Hello")
        }
        static("/static") {
            resources()
        }
    }
}