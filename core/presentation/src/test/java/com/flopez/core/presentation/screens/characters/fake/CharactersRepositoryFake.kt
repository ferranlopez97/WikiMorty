package com.flopez.core.presentation.screens.characters.fake

import com.flopez.core.domain.model.Character
import com.flopez.core.domain.model.QueryFilter
import com.flopez.core.domain.pagination.PageResult
import com.flopez.core.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class CharactersRepositoryFake : CharactersRepository {

    val characters = mutableListOf<Character>()

    override suspend fun loadNextPage(filter: QueryFilter?): PageResult {
        return PageResult.ITEMS_AVAILABLE
    }

    override fun getAllCharacters(filter: QueryFilter?): Flow<List<Character>> {
        return when (filter) {
                is QueryFilter.ByName -> MutableStateFlow<List<Character>>(characters).map { it.filter { it.name.contains(filter.name, true) } }
                else -> MutableStateFlow<List<Character>>(characters)
            }

    }

    override fun getCharacterById(id: Long): Character? {
        return null
    }
}