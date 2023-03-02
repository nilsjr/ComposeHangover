package de.nilsdruyen.compose.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.nilsdruyen.compose.common.HangoverViewModel
import de.nilsdruyen.compose.desktop.components.GradientShadowButton
import de.nilsdruyen.compose.desktop.theme.DesktopTheme

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

    DesktopTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Color: $colorValue",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colors.onBackground
            )
            Box(Modifier.fillMaxWidth().height(50.dp).background(validColor))
            GradientShadowButton {
                Text(
                    text = "Hover me for magic",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}