package com.flopez.core.domain.usecase

import com.flopez.core.domain.exception.MortyException
import com.flopez.core.domain.model.Character
import com.flopez.core.domain.result.Result

interface GetCharacterByIdUseCase {
    suspend fun execute(id: Long) : Result<Character, MortyException>
}