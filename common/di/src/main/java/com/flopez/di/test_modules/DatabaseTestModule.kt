package com.flopez.di.test_modules

import android.content.Context
import androidx.room.Room
import com.flopez.core.database.WikiMortyDatabase
import com.flopez.core.database.dao.CharactersDAO
import com.flopez.core.database.dao.LocationDAO
import com.flopez.di.modules.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object DatabaseTestModule {

    @Provides
    fun provideInMemoryDatabase(@ApplicationContext context: Context): WikiMortyDatabase {
        return Room.inMemoryDatabaseBuilder(context, WikiMortyDatabase::class.java)
            .allowMainThreadQueries()
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    @Singleton
    @Provides
    fun charactersDAO(db: WikiMortyDatabase) : CharactersDAO = db.charactersDAO()

    @Singleton
    @Provides
    fun locationsDAO(db: WikiMortyDatabase) : LocationDAO = db.locationDAO()

}


