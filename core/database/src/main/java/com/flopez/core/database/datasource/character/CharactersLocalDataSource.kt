package com.flopez.core.database.datasource.character

import com.flopez.core.database.entity.CharacterEntity
import com.flopez.core.database.entity.CharacterWithLocations
import kotlinx.coroutines.flow.Flow

interface CharactersLocalDataSource {
    suspend fun insertAll(characters: List<CharacterEntity>)
    fun getCharactersByName(name: String) : Flow<List<CharacterWithLocations>>
    fun getAllCharacters() : Flow<List<CharacterWithLocations>>
    fun getById(id: Long) : CharacterWithLocations?
    suspend fun clearAll()
    fun countCharacters() : Int
}