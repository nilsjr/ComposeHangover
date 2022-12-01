package de.nilsdruyen.compose

import de.nilsdruyen.compose.html.dashboard
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.html.HTML
import org.slf4j.event.Level

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port = port) { module() }.start(true)
}

fun Headers.header(flag: String): String = this[flag] ?: "empty"

@SuppressWarnings("LongMethod")
fun Application.module() {
    val mutableColor: MutableStateFlow<String> = MutableStateFlow("FFFFFF")
    val color: StateFlow<String> = mutableColor.asStateFlow()

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
        format { call ->
            val userAgent = call.request.headers.header("User-Agent")
            val requestPath = call.request.path()
            "ID:$userAgent - $requestPath"
        }
    }
    install(DefaultHeaders)
    install(Compression) {
        gzip { priority = 1.0 }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }
    install(WebSockets)

    routing {
        static("static") {
            resources()
        }
        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::dashboard)
        }
        post("/color/{value}") {
            val colorParam = call.parameters["value"].toString()
            mutableColor.value = colorParam
            call.respond(HttpStatusCode.OK)
        }
        webSocket("/design") {
            color.collect { color ->
                println("send color: $color")
                outgoing.send(Frame.Text(color))
            }
        }
    }
}