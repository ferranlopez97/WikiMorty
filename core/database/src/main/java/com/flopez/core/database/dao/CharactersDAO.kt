package com.flopez.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.flopez.core.database.entity.CharacterEntity
import com.flopez.core.database.entity.CharacterWithLocations
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDAO {

    @Transaction
    @Query("SELECT * FROM CharacterEntity WHERE name LIKE '%' || :name || '%'")
    fun getCharactersByName(name: String) : Flow<List<CharacterWithLocations>>

    @Transaction
    @Query("SELECT * FROM CharacterEntity")
    fun getAllCharacters() : Flow<List<CharacterWithLocations>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CharacterEntity>)

    @Query("DELETE FROM CharacterEntity")
    suspend fun clearAll()

    @Query("SELECT * FROM CharacterEntity WHERE id = :id")
    fun getById(id: Long) : CharacterWithLocations?

    @Query("SELECT COUNT(*) FROM CharacterEntity")
    fun countCharacters(): Int
}