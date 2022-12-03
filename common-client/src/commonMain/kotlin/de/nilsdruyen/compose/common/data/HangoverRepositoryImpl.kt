package de.nilsdruyen.compose.common.data

import de.nilsdruyen.compose.common.api.ClientApi
import de.nilsdruyen.compose.common.entities.ThemeEntity
import kotlinx.coroutines.flow.Flow

class HangoverRepositoryImpl : HangoverRepository {

    override fun observeStyle(): Flow<ThemeEntity> = ClientApi.observeTheme()
}