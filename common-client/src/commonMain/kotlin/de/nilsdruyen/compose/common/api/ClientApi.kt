package de.nilsdruyen.compose.common.api

import de.nilsdruyen.compose.common.entities.ThemeEntity
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.serialization.json.Json

expect fun createClient(block: HttpClientConfig<*>.() -> Unit): HttpClient

internal object ClientApi {

    private val client = createClient {
        install(WebSockets) {
            pingInterval = 20_000
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    internal fun observeColor(): Flow<String> {
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

    internal fun observeTheme(): Flow<ThemeEntity> {
        return callbackFlow {
            var proceed = true
            client.webSocket(method = HttpMethod.Get, host = "192.168.178.138", port = 8080, path = "/theme") {
                while (proceed) {
                    val themeEntity: ThemeEntity = receiveDeserialized()
                    trySend(themeEntity)
                }
            }
            awaitClose {
                proceed = false
            }
        }
    }
}