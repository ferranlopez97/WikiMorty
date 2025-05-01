package com.flopez.core.presentation.screens.characters.fake

import com.flopez.core.domain.exception.MortyException
import com.flopez.core.domain.model.QueryFilter
import com.flopez.core.domain.pagination.PageResult
import com.flopez.core.domain.result.Result
import com.flopez.core.domain.usecase.QueryPagedCharactersUseCase

class GetPagedCharactersUseCaseFake : QueryPagedCharactersUseCase {

    var exception : MortyException? = null

    override suspend fun execute(filter: QueryFilter?): Result<PageResult, MortyException> {
        return exception?.let { Result.Error(it) } ?: Result.Success(PageResult.ITEMS_AVAILABLE)
    }
}