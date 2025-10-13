package com.flopez.di.modules

import com.flopez.core.domain.dispatcher.DispatcherProvider
import com.flopez.core.domain.dispatcher.StandardDispatchers
import com.flopez.core.domain.feature.remoteconfig.IsFeatureEnabledUseCase
import com.flopez.core.domain.repository.RemoteConfigRepository
import com.flopez.core.domain.usecase.GetCharacterByIdUseCase
import com.flopez.core.domain.usecase.GetCharacterByIdUseCaseImpl
import com.flopez.core.domain.usecase.InitializeConfigUseCase
import com.flopez.core.domain.usecase.QueryPagedCharactersUseCase
import com.flopez.core.domain.usecase.QueryPagedCharactersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DomainAbstractModule {

    @Binds
    abstract fun getCharactersUseCase(
        getCharactersUseCaseImpl: QueryPagedCharactersUseCaseImpl
    ) : QueryPagedCharactersUseCase


    @Binds
    abstract fun getCharacterByIdUseCase(
        getCharacterByIdUseCase: GetCharacterByIdUseCaseImpl
    ) : GetCharacterByIdUseCase

    @Binds
    abstract fun standardDispatcherProvider(standardDispatchers: StandardDispatchers) : DispatcherProvider
}

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideInitializeConfigUseCase(
        remoteConfigRepository: RemoteConfigRepository
    ) : InitializeConfigUseCase {
        return InitializeConfigUseCase(remoteConfigRepository)
    }

    @Provides
    fun provideIsFeatureEnabledUseCase(
        remoteConfigRepository: RemoteConfigRepository
    ) : IsFeatureEnabledUseCase {
        return IsFeatureEnabledUseCase(remoteConfigRepository)
    }
}


