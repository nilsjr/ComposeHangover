package de.nilsdruyen.compose.common.model

data class HangoverState(
    val inSync: Boolean = false,
    val theme: Theme = Theme(),
    val events: Set<String> = emptySet(),
    val consumed: Set<String> = emptySet(),
)
