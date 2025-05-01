package com.flopez.core.domain.usecase

import com.flopez.core.domain.dispatcher.DispatcherProvider
import com.flopez.core.domain.exception.CharacterQueryException
import com.flopez.core.domain.exception.MortyException
import com.flopez.core.domain.model.Character
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.domain.result.Result
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCharacterByIdUseCaseImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val charactersRepository: CharactersRepository
) : GetCharacterByIdUseCase {

    override suspend fun execute(id: Long): Result<Character, MortyException> {

        return withContext(dispatcherProvider.IO) {
            try {
                val character = charactersRepository.getCharacterById(id)

                if (character != null) {
                    Result.Success(character)
                } else {
                    Result.Error(CharacterQueryException.QuerySingleCharacterException(id))
                }
            }  catch (e: Exception) {
                Result.Error(CharacterQueryException.QuerySingleCharacterException(id))
            }
        }

    }
}