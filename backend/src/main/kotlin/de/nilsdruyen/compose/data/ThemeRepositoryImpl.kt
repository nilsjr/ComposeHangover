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
    private val localTheme: StateFlow<ThemeEntity> = mutableTheme.asStateFlow()

    override fun observeTheme(): Flow<ThemeEntity> = localTheme

    override fun setColor(hex: String) {
        val colors = localTheme.value.colors.toMutableMap()
        colors[Color.PRIMARY] = hex
        mutableTheme.value = localTheme.value.copy(
            colors = colors
        )
    }

    override fun updateTheme(theme: UpdateThemeEntity) {
        val (colors, _, _) = localTheme.value

        val mutableColors = colors.toMutableMap()
        theme.colors?.let {
            mutableColors.putAll(it)
        }
        theme.shapes?.let {

        }
        theme.typography?.let {

        }

        mutableTheme.value = localTheme.value.copy(
            colors = mutableColors,
        )
    }
}