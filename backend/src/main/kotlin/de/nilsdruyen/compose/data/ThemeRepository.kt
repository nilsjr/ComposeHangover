package de.nilsdruyen.compose.data

import de.nilsdruyen.compose.common.entities.ThemeEntity
import de.nilsdruyen.compose.entities.UpdateThemeEntity
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {

    fun observeTheme(): Flow<ThemeEntity>

    fun setColor(hex: String)

    fun updateTheme(theme: UpdateThemeEntity)
}