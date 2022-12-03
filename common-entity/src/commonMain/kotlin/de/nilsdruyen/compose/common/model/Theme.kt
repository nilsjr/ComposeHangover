package de.nilsdruyen.compose.common.model

import de.nilsdruyen.compose.common.entities.Color

data class Theme(
    val colors: Map<Color, String> = emptyMap(),
    val shapes: Map<String, Int> = emptyMap(),
    val typography: Map<String, String> = emptyMap(),
)