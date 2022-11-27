package de.nilsdruyen.compose.common.api

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.websocket.WebSockets

expect fun createClient(block: HttpClientConfig<*>.() -> Unit): HttpClient

object ClientApi {

    val client = createClient {
        install(WebSockets) {
            pingInterval = 20_000
        }
    }
}