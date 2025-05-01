package com.flopez.di.modules

import android.content.Context
import androidx.room.Room
import com.flopez.core.database.WikiMortyDatabase
import com.flopez.core.database.WikiMortyDatabase.Companion.DATABASE_NAME
import com.flopez.core.database.dao.CharactersDAO
import com.flopez.core.database.dao.LocationDAO
import com.flopez.core.database.datasource.character.CharactersLocalDataSource
import com.flopez.core.database.datasource.character.CharactersRoomDataSource
import com.flopez.core.database.datasource.location.LocationRoomDataSource
import com.flopez.core.database.datasource.location.LocationsLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): WikiMortyDatabase {
        return Room.databaseBuilder(
            context,
            WikiMortyDatabase::class.java,
            DATABASE_NAME
        ).build()
    }


    @Singleton
    @Provides
    fun charactersDAO(db: WikiMortyDatabase) : CharactersDAO = db.charactersDAO()

    @Singleton
    @Provides
    fun locationsDAO(db: WikiMortyDatabase) : LocationDAO = db.locationDAO()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseAbstractModule {


    @Binds
    @Singleton
    abstract fun provideCharactersLocalDataSource(localDataSource: CharactersRoomDataSource) : CharactersLocalDataSource

    @Binds
    @Singleton
    abstract fun provideLocationLocalDataSource(localDataSource: LocationRoomDataSource) : LocationsLocalDataSource
}