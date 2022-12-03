package de.nilsdruyen.compose.common

import de.nilsdruyen.compose.common.api.entities.ThemeEntity
import de.nilsdruyen.compose.common.data.HangoverRepository
import de.nilsdruyen.compose.common.model.Color
import de.nilsdruyen.compose.common.model.HangoverState
import de.nilsdruyen.compose.common.model.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HangoverViewModel(
    private val scope: CoroutineScope,
    private val hangoverRepository: HangoverRepository,
) {

    private val _state: MutableStateFlow<HangoverState> = MutableStateFlow(HangoverState())
    val state: StateFlow<HangoverState> = _state.asStateFlow()

    fun observe() {
        _state.value = state.value.copy(inSync = true)
        scope.launch {
            try {
                hangoverRepository.observeStyle().collect {
                    _state.value = state.value.copy(theme = it.map())
                }
                _state.value = state.value.copy(inSync = false)
            } catch (e: Exception) {
                println("error websocket ${e.message}")
                _state.value = state.value.copy(inSync = false)
            }
        }
    }
}

fun ThemeEntity.map(): Theme {
    return Theme(
        colors = colors.mapKeys {
            it.key.toColor()
        }
    )
}

fun String.toColor(): Color {
    return when (this) {
        "primary" -> Color.PRIMARY
        "secondary" -> Color.SECONDARY
        "tertiary" -> Color.TERTIARY
        else -> error("not matching")
    }
}