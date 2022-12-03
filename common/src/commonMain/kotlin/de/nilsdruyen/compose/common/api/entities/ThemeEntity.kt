package de.nilsdruyen.compose.common.api.entities

import kotlinx.serialization.Serializable

@Serializable
data class ThemeEntity(
    val colors: Map<String, String>,
    val shapes: Map<String, String>,
    val typography: Map<String, String>,
)
