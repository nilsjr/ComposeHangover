package de.nilsdruyen.compose.common.api

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlin.coroutines.suspendCoroutine

expect fun createClient(block: HttpClientConfig<*>.() -> Unit): HttpClient

object ClientApi {

    private val client = createClient {
        install(WebSockets) {
            pingInterval = 20_000
        }
    }

    fun observeColor(): Flow<String> {
        return callbackFlow {
            client.webSocket(method = HttpMethod.Get, host = "localhost", port = 8080, path = "/design") {
                incoming.consumeAsFlow().collect {
                    if (it is Frame.Text) {
                        trySend(it.readText())
                    }
                }
            }
        }
    }
}