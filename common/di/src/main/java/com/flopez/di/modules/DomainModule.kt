package com.flopez.di.modules

import com.flopez.core.domain.dispatcher.DispatcherProvider
import com.flopez.core.domain.dispatcher.StandardDispatchers
import com.flopez.core.domain.usecase.GetCharacterByIdUseCase
import com.flopez.core.domain.usecase.GetCharacterByIdUseCaseImpl
import com.flopez.core.domain.usecase.QueryPagedCharactersUseCase
import com.flopez.core.domain.usecase.QueryPagedCharactersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

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


