package de.nilsdruyen.compose.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import de.nilsdruyen.compose.android.ui.ComposePartyTheme
import de.nilsdruyen.compose.android.ui.getColor
import de.nilsdruyen.compose.android.ui.toHexCode
import de.nilsdruyen.compose.common.HangoverViewModel
import de.nilsdruyen.compose.common.data.HangoverRepositoryImpl
import de.nilsdruyen.compose.common.model.HangoverState
import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.NetworkInterface

class MainActivity : ComponentActivity() {

    private val viewModel = HangoverViewModel(lifecycleScope, HangoverRepositoryImpl())

    private val server by lazy {
        embeddedServer(Netty, 13276, watchPaths = emptyList()) {
            routing {
                get("/") {
                    call.respondText("All good here in ${Build.MODEL}", ContentType.Text.Plain)
                }
                get("/test") {
                    val value = execute()
                    call.respondText("Hello Nils $value", ContentType.Text.Plain)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsState()

            LaunchedEffect(state.serverIsRunning) {
                if (state.serverIsRunning) {
                    startServer()
                } else {
                    server.stop(1_000, 2_000)
                }
            }

            ComposePartyTheme(state.theme) {
                App(
                    state = state,
                    connect = { viewModel.observe() },
                    disconnect = { viewModel.disconnect() },
                    startServer = { viewModel.startServer() },
                    stopServer = { viewModel.stopServer() },
                )
            }
        }
    }

    private fun startServer() {
        CoroutineScope(Dispatchers.IO).launch {
            server.start(wait = true)
        }
    }

    override fun onStop() {
        super.onStop()

        server.stop(1_000, 2_000)
        viewModel.stopServer()
    }

    private suspend fun execute(): Int {
        delay(2_000)
        return 10
    }
}

private fun getIpAddressInLocalNetwork(): String? {
    val networkInterfaces = NetworkInterface.getNetworkInterfaces().iterator().asSequence()
    val localAddresses = networkInterfaces.flatMap {
        it.inetAddresses.asSequence()
            .filter { inetAddress ->
                inetAddress.isSiteLocalAddress && !inetAddress.hostAddress!!.contains(":") &&
                    inetAddress.hostAddress != "127.0.0.1"
            }
            .map { inetAddress -> inetAddress.hostAddress }
    }
    return localAddresses.joinToString(", ")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    state: HangoverState,
    connect: () -> Unit,
    disconnect: () -> Unit,
    startServer: () -> Unit,
    stopServer: () -> Unit,
) {
    val ipAddress = remember { getIpAddressInLocalNetwork() }

    LaunchedEffect(Unit) {
        connect()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Compose Hangover")
                },
                actions = {
                    Switch(checked = state.inSync, onCheckedChange = {
                        if (it) connect() else disconnect()
                    })
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Color: ${state.theme.colors[de.nilsdruyen.compose.common.entities.Color.PRIMARY]}",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                "Color: ${MaterialTheme.colorScheme.primary.toHexCode()}",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "Colored Text",
                color = state.theme.colors[de.nilsdruyen.compose.common.entities.Color.PRIMARY]?.getColor()
                    ?: Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            HorizontalDivider()
            Text(
                text = "IP: $ipAddress",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
            Switch(
                checked = state.serverIsRunning,
                onCheckedChange = {
                    if (it) startServer() else stopServer()
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}