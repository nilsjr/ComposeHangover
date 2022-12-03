package de.nilsdruyen.compose.data

import de.nilsdruyen.compose.common.entities.Color
import de.nilsdruyen.compose.common.entities.DefaultTheme
import de.nilsdruyen.compose.common.entities.ThemeEntity
import de.nilsdruyen.compose.entities.UpdateThemeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeRepositoryImpl : ThemeRepository {

    private val mutableTheme = MutableStateFlow(DefaultTheme)
    private val theme: StateFlow<ThemeEntity> = mutableTheme.asStateFlow()

    override fun observeTheme(): Flow<ThemeEntity> = theme

    override fun setColor(hex: String) {
        val colors = theme.value.colors.toMutableMap()
        colors[Color.PRIMARY] = hex
        mutableTheme.value = theme.value.copy(
            colors = colors
        )
    }

    override fun updateTheme(updateThemeEntity: UpdateThemeEntity) {
        val (colors, _, _) = theme.value

        val mutableColors = colors.toMutableMap()
        updateThemeEntity.colors?.let {
            mutableColors.putAll(it)
        }
        updateThemeEntity.shapes?.let {

        }
        updateThemeEntity.typography?.let {

        }

        mutableTheme.value = theme.value.copy(
            colors = mutableColors,
        )
    }
}