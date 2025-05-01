package com.flopez.core.domain.usecase

import com.flopez.core.domain.dispatcher.DispatcherProvider
import com.flopez.core.domain.exception.CharacterQueryException
import com.flopez.core.domain.exception.MortyException
import com.flopez.core.domain.model.QueryFilter
import com.flopez.core.domain.pagination.PageResult
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.domain.result.Result
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QueryPagedCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val dispatcherProvider: DispatcherProvider
) : QueryPagedCharactersUseCase {

    override suspend fun execute(filter: QueryFilter?): Result<PageResult, MortyException> {

        return withContext(dispatcherProvider.IO) {
            try {
                val result = charactersRepository.loadNextPage(filter)
                Result.Success(result)

            } catch (e: MortyException) {
                Result.Error(e)
            } catch (e: Exception) {
                Result.Error(CharacterQueryException.QueryCharactersListException)
            }
        }

    }
}