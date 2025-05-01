package com.flopez.core.presentation.di

import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.presentation.fake.CharacterRepositoryFake
import com.flopez.di.modules.DataModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
object DataTestModule {

    @Provides
    fun charactersRepository() : CharactersRepository {
        return CharacterRepositoryFake()
    }
}