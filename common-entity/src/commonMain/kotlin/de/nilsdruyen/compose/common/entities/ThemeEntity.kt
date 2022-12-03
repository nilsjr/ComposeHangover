package de.nilsdruyen.compose.common.entities

import kotlinx.serialization.Serializable

@Serializable
data class ThemeEntity(
    val colors: Map<Color, String>,
    val shapes: Map<String, String>,
    val typography: Map<String, String>,
)

val DefaultTheme = ThemeEntity(
    colors = mapOf(
        Color.PRIMARY to "6750A4",
        Color.SECONDARY to "958DA5",
        Color.TERTIARY to "B58392",
    ),
    shapes = mapOf(),
    typography = mapOf(),
)