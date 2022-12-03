package de.nilsdruyen.compose.entities

import de.nilsdruyen.compose.common.entities.Color
import kotlinx.serialization.Serializable

@Serializable
data class UpdateThemeEntity(
    val colors: Map<Color, String>? = null,
    val shapes: Map<String, String>? = null,
    val typography: Map<String, String>? = null,
)
