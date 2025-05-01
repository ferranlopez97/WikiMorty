package com.flopez.core.presentation.fake

import com.flopez.core.domain.model.Character
import com.flopez.core.domain.model.QueryFilter
import com.flopez.core.domain.pagination.PageResult
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.presentation.generator.fakeCharacters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CharacterRepositoryFake : CharactersRepository {

    var pageResult : PageResult = PageResult.ITEMS_AVAILABLE

    val characters = fakeCharacters().toMutableList()

    override suspend fun loadNextPage(filter: QueryFilter?): PageResult {
        return pageResult
    }

    override fun getAllCharacters(filter: QueryFilter?): Flow<List<Character>> {
        println("getAllCharacters $characters")
        return flowOf(characters)
    }

    override fun getCharacterById(id: Long): Character? {
        return characters.find { it.id == id }
    }
}