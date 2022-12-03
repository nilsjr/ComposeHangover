package de.nilsdruyen.compose.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

class MainActivity : ComponentActivity() {

    private val viewModel = HangoverViewModel(lifecycleScope, HangoverRepositoryImpl())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            ComposePartyTheme(state.theme) {
                App(
                    state = state,
                    connect = { viewModel.observe() },
                    disconnect = { viewModel.disconnect() },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(state: HangoverState, connect: () -> Unit, disconnect: () -> Unit) {
//    val platformName = getPlatformName()
//    var text by remember { mutableStateOf("Hello, World!") }

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
                        if (it) {
                            connect()
                        } else {
                            disconnect()
                        }
                    })
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Text(
                "Color: ${state.theme.colors[de.nilsdruyen.compose.common.entities.Color.PRIMARY]}",
                modifier = Modifier.padding(16.dp)
            )
            Text(
                "Color: ${MaterialTheme.colorScheme.primary.toHexCode()}",
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Colored Text",
                color = state.theme.colors[de.nilsdruyen.compose.common.entities.Color.PRIMARY]?.getColor() ?: Color.Gray,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}