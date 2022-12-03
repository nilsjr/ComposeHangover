package de.nilsdruyen.compose

import de.nilsdruyen.compose.data.ThemeRepository
import de.nilsdruyen.compose.data.ThemeRepositoryImpl
import de.nilsdruyen.compose.entities.UpdateThemeEntity
import de.nilsdruyen.compose.html.dashboard
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
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
import kotlinx.html.InputType
import kotlinx.serialization.json.Json
import org.slf4j.event.Level
import java.time.Duration
import java.util.zip.Deflater

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port = port) { module() }.start(true)
}

fun Headers.header(flag: String): String = this[flag] ?: "empty"

@SuppressWarnings("LongMethod")
fun Application.module() {
    val repository: ThemeRepository = ThemeRepositoryImpl()

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
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            }
        )
    }
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
        extensions {
            install(WebSocketDeflateExtension) {
                compressionLevel = Deflater.DEFAULT_COMPRESSION
                compressIfBiggerThan(bytes = 4 * 1024)
            }
        }
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }
    routing {
        static("static") {
            resources()
        }
        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::dashboard)
        }
        post("/color/{value}") {
            val colorParam = call.parameters["value"].toString()
            repository.setColor(colorParam)
            call.respond(HttpStatusCode.OK)
        }
        post("/theme") {
            val theme = call.receive<UpdateThemeEntity>()
            repository.updateTheme(theme)
            val colorParam = call.parameters["value"].toString()
            repository.setColor(colorParam)
            call.respond(HttpStatusCode.OK)
        }
        webSocket("/theme") {
            repository.observeTheme().collect { theme ->
                sendSerialized(theme)
            }
        }
    }
}