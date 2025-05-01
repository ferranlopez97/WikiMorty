package com.flopez.core.database.datasource.character

import com.flopez.core.database.dao.CharactersDAO
import com.flopez.core.database.entity.CharacterEntity
import com.flopez.core.database.entity.CharacterWithLocations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CharactersRoomDataSource @Inject constructor(
    private val dao: CharactersDAO
) : CharactersLocalDataSource {

    override suspend fun insertAll(characters: List<CharacterEntity>) = dao.insertAll(characters)

    override fun getAllCharacters(): Flow<List<CharacterWithLocations>> = dao.getAllCharacters()

    override fun getCharactersByName(name: String): Flow<List<CharacterWithLocations>> = dao.getCharactersByName(name)

    override fun getById(id: Long): CharacterWithLocations? = dao.getById(id)

    override suspend fun clearAll() = dao.clearAll()

    override fun countCharacters(): Int = dao.countCharacters()


}