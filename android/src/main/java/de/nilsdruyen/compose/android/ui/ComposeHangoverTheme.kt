package de.nilsdruyen.compose.android.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.nilsdruyen.compose.common.model.Theme
import de.nilsdruyen.compose.common.model.Color as HangoverColor

@Composable
fun ComposePartyTheme(theme: Theme, content: @Composable () -> Unit) {
    val colorScheme = theme.colors.mapToScheme()

    val typography = Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
    )

    val shapes = Shapes(
        small = RoundedCornerShape(16.dp),
    )

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        typography = typography,
        content = content
    )
}

const val DefaultColor = "ffffff"
private fun Map<HangoverColor, String>.mapToScheme(): ColorScheme {
    return lightColorScheme(
        primary = getOrDefault(HangoverColor.PRIMARY, DefaultColor).getColor(),
        secondary = getOrDefault(HangoverColor.SECONDARY, DefaultColor).getColor(),
        tertiary = getOrDefault(HangoverColor.TERTIARY, DefaultColor).getColor(),
    )
}

fun String.getColor(): Color {
    return try {
        Color(android.graphics.Color.parseColor("#$this"))
    } catch (e: Exception) {
        Color.White
    }
}

fun Color.toHexCode(): String {
    val red = this.red * 255
    val green = this.green * 255
    val blue = this.blue * 255
    return String.format("#%02x%02x%02x", red.toInt(), green.toInt(), blue.toInt())
}