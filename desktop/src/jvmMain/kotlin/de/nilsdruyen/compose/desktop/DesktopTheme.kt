package de.nilsdruyen.compose.desktop

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun App() {
    MaterialTheme(colors = darkColors()) {
        Surface(
            color = Color(0xff111827)
        ) {
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
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
}