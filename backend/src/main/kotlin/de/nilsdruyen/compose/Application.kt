package de.nilsdruyen.compose

import de.nilsdruyen.compose.html.dashboard
import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.compression.deflate
import io.ktor.server.plugins.compression.gzip
import io.ktor.server.plugins.compression.minimumSize
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.request.path
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.html.HTML
import org.slf4j.event.Level
import java.util.Collections
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port = port) { module() }.start(true)
}

fun Headers.header(flag: String): String = this[flag] ?: "empty"

@SuppressWarnings("LongMethod")
fun Application.module() {
    val _color: MutableStateFlow<String> = MutableStateFlow("FFFFFF")
    val color: StateFlow<String> = _color.asStateFlow()

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
            _color.value = colorParam
            call.respond(HttpStatusCode.OK)
        }
        webSocket("/design") {
            color.collect { color ->
                outgoing.send(Frame.Text(color))
            }
        }
    }
}