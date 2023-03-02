package de.nilsdruyen.compose.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.nilsdruyen.compose.common.HangoverViewModel

@Composable
fun App(viewModel: HangoverViewModel) {
    val state by viewModel.state.collectAsState()

    val colorValue = remember {
        derivedStateOf {
            state.theme.colors[de.nilsdruyen.compose.common.entities.Color.PRIMARY]
        }
    }
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
        viewModel.observe()
    }

    Column(
        Modifier.fillMaxSize().background(validColor)
    ) {
        Text("Color: $colorValue", modifier = Modifier.padding(16.dp))
        // TODO: implement ui
    }
}