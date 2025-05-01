package com.flopez.core.domain.repository

import com.flopez.core.domain.model.Character
import com.flopez.core.domain.model.QueryFilter
import com.flopez.core.domain.pagination.PageResult
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun loadNextPage(filter: QueryFilter? = null) : PageResult
    fun getAllCharacters(filter: QueryFilter? = null) : Flow<List<Character>>
    fun getCharacterById(id: Long) : Character?
}