package de.nilsdruyen.compose.common.data

import de.nilsdruyen.compose.common.api.entities.ThemeEntity
import de.nilsdruyen.compose.common.model.Theme
import kotlinx.coroutines.flow.Flow

interface HangoverRepository {

    fun observeStyle(): Flow<ThemeEntity>
}