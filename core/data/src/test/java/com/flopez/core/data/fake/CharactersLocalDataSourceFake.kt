package com.flopez.core.data.fake

import com.flopez.core.database.datasource.character.CharactersLocalDataSource
import com.flopez.core.database.entity.CharacterEntity
import com.flopez.core.database.entity.CharacterWithLocations
import com.flopez.core.database.entity.LocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CharactersLocalDataSourceFake : CharactersLocalDataSource {

    private val charactersList : MutableList<CharacterEntity> = mutableListOf()

    override suspend fun insertAll(characters: List<CharacterEntity>) {
        charactersList.addAll(characters)
    }

    override fun getCharactersByName(name: String): Flow<List<CharacterWithLocations>> {
        return flowOf(charactersList.filter { it.name.contains(name, true) }.map { it.withLocations() })
    }

    override fun getAllCharacters(): Flow<List<CharacterWithLocations>> {
        return flowOf(charactersList.map { it.withLocations() })
    }

    override fun getById(id: Long): CharacterWithLocations {
        return charactersList.first { it.id == id }.withLocations()
    }

    override suspend fun clearAll() {
        charactersList.clear()
    }

    override fun countCharacters(): Int {
        return charactersList.size
    }

    private fun CharacterEntity.withLocations() : CharacterWithLocations {
        return CharacterWithLocations(
            character = this,
            originLocation = LocationEntity("originLocation", ""),
            currentLocation = LocationEntity("currentLocation", "")
        )
    }
}