package com.flopez.core.presentation.screens.characters.fake

import com.flopez.core.domain.exception.MortyException
import com.flopez.core.domain.model.Character
import com.flopez.core.domain.result.Result
import com.flopez.core.domain.usecase.GetCharacterByIdUseCase
import com.flopez.core.presentation.util.preview.fakeCharacter

class GetCharacterByIdUseCaseFake : GetCharacterByIdUseCase {

    var exception: MortyException? = null

    override suspend fun execute(id: Long): Result<Character, MortyException> {
        return exception?.let { Result.Error(it) } ?: Result.Success(fakeCharacter)
    }
}