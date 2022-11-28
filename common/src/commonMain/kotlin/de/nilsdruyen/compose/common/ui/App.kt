package de.nilsdruyen.compose.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.nilsdruyen.compose.common.api.ClientApi
import de.nilsdruyen.compose.common.getPlatformName

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    var colorValue by remember { mutableStateOf("#000000") }
    val platformName = getPlatformName()

    LaunchedEffect(Unit) {
        try {
            ClientApi.observeColor().collect {
                colorValue = it
            }
        } catch (e: Exception) {
            println("error websocket ${e.message}")
        }
    }

    Column {
        Text("Color: $colorValue", modifier = Modifier.padding(16.dp))
        Button(onClick = {
            text = "Hello, $platformName"
        }) {
            Text(text)
        }
    }
}
