package com.flopez.di.modules

import com.flopez.core.data.repository.CharactersRepositoryImpl
import com.flopez.core.data.repository.RemoteConfigRepositoryImpl
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.domain.repository.RemoteConfigRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun charactersRepository(charactersRepository: CharactersRepositoryImpl) : CharactersRepository

    @Binds
    @Singleton
    abstract fun remoteConfigRepository(remoteConfigRepository: RemoteConfigRepositoryImpl) : RemoteConfigRepository

}