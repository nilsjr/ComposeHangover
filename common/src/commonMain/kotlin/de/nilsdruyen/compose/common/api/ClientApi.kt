package de.nilsdruyen.compose.common.api

import de.nilsdruyen.compose.common.api.entities.ThemeEntity
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.websocket.*
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.*
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.serialization.json.Json

expect fun createClient(block: HttpClientConfig<*>.() -> Unit): HttpClient

object ClientApi {

    private val client = createClient {
        install(WebSockets) {
            pingInterval = 20_000
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    fun observeColor(): Flow<String> {
        return callbackFlow {
            client.webSocket(method = HttpMethod.Get, host = "192.168.178.138", port = 8080, path = "/design") {
                incoming.consumeAsFlow().collect {
                    if (it is Frame.Text) {
                        trySend(it.readText())
                    }
                }
            }
        }
    }

    fun observeTheme(): Flow<ThemeEntity> {
        return callbackFlow {
            client.webSocket(method = HttpMethod.Get, host = "192.168.178.138", port = 8080, path = "/theme") {
                while (true) {
                    val themeEntity: ThemeEntity = receiveDeserialized()
                    trySend(themeEntity)
                }
            }
        }
    }
}