package de.nilsdruyen.compose.common.data

import de.nilsdruyen.compose.common.entities.ThemeEntity
import kotlinx.coroutines.flow.Flow

interface HangoverRepository {

    fun observeStyle(): Flow<ThemeEntity>
}