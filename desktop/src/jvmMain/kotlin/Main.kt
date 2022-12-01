import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.nilsdruyen.compose.common.api.ClientApi
import de.nilsdruyen.compose.common.getPlatformName

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    var colorValue by remember { mutableStateOf("000000") }
    val platformName = getPlatformName()

    val validColor by remember {
        derivedStateOf {
            try {
                val hashColorString = "#$colorValue"
                val color = Color(hashColorString.removePrefix("#").toLong(16) or 0x00000000FF000000)
                color
            } catch (e: Exception) {
                Color.White
            }
        }
    }

    LaunchedEffect(Unit) {
        try {
            ClientApi.observeColor().collect {
                println("update color: $it")
                colorValue = it
            }
        } catch (e: Exception) {
            println("error websocket ${e.message}")
        }
    }

    Column(
        Modifier.fillMaxSize().background(validColor)
    ) {
        Text("Color: $colorValue", modifier = Modifier.padding(16.dp))
        Button(onClick = {
            text = "Hello, $platformName"
        }) {
            Text(text)
        }
    }
}