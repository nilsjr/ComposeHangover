package de.nilsdruyen.compose.common.api

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

actual fun createClient(block: HttpClientConfig<*>.() -> Unit): HttpClient {
    return HttpClient(Darwin, block)
}