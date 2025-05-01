package com.flopez.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flopez.core.database.dao.CharactersDAO
import com.flopez.core.database.dao.LocationDAO
import com.flopez.core.database.entity.CharacterEntity
import com.flopez.core.database.entity.LocationEntity

@Database(
    entities = [
        LocationEntity::class,
        CharacterEntity::class
    ],
    version = 1
)
abstract class WikiMortyDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "wikimorty_db"
    }

    abstract fun charactersDAO() : CharactersDAO
    abstract fun locationDAO() : LocationDAO
}