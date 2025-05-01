package com.flopez.core.domain.usecase

import com.flopez.core.domain.exception.MortyException
import com.flopez.core.domain.model.QueryFilter
import com.flopez.core.domain.pagination.PageResult
import com.flopez.core.domain.result.Result


interface QueryPagedCharactersUseCase {
    suspend fun execute(filter: QueryFilter? = null) : Result<PageResult, MortyException>
}