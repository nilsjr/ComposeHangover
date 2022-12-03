package de.nilsdruyen.compose.data

import de.nilsdruyen.compose.entities.ThemeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

interface ThemeRepository {

    fun observeTheme(): Flow<ThemeEntity>

    fun setColor(hex: String)
}

class ThemeRepositoryImpl : ThemeRepository {

    private val mutableColor: MutableStateFlow<String> = MutableStateFlow("FFFFFF")
    private val color: StateFlow<String> = mutableColor.asStateFlow()

    override fun observeTheme(): Flow<ThemeEntity> {
        return color.map {
            ThemeEntity(
                colors = mapOf("primary" to it),
                shapes = emptyMap(),
                typography = emptyMap(),
            )
        }
    }

    override fun setColor(hex: String) {
        mutableColor.value = hex
    }
}