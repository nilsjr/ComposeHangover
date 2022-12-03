package de.nilsdruyen.compose.common.data

import de.nilsdruyen.compose.common.api.ClientApi
import de.nilsdruyen.compose.common.api.entities.ThemeEntity
import de.nilsdruyen.compose.common.model.Color
import de.nilsdruyen.compose.common.model.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HangoverRepositoryImpl : HangoverRepository {

    override fun observeStyle(): Flow<ThemeEntity> {
        return ClientApi.observeTheme()
    }
}