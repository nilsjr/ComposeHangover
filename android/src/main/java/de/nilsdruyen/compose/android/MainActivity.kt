package de.nilsdruyen.compose.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.nilsdruyen.compose.common.api.ClientApi
import de.nilsdruyen.compose.common.getPlatformName

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    var colorValue by remember { mutableStateOf("ffffff") }
    val platformName = getPlatformName()

    val validColor by remember {
        derivedStateOf {
            try {
                val color = getColor(colorValue)
                color
            } catch (e: Exception) {
                Color.White
            }
        }
    }

    LaunchedEffect(Unit) {
        try {
            ClientApi.observeColor().collect {
                colorValue = it
            }
        } catch (e: Exception) {
            println("error websocket ${e.message}")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(validColor)
    ) {
        Text("Color: $colorValue", modifier = Modifier.padding(16.dp))
        Button(onClick = {
            text = "Hello, $platformName"
        }) {
            Text(text)
        }
    }
}

fun getColor(colorString: String): Color {
    return Color(android.graphics.Color.parseColor("#$colorString"))
}