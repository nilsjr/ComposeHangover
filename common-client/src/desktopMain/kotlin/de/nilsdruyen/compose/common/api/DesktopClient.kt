package de.nilsdruyen.compose.common.api

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO

actual fun createClient(block: HttpClientConfig<*>.() -> Unit): HttpClient {
    return HttpClient(CIO, block)
}