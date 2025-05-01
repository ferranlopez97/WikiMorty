package com.flopez.core.presentation.screens.characters.detail.model

import androidx.compose.runtime.Immutable
import com.flopez.core.domain.exception.MortyException
import com.flopez.core.domain.model.Character
import com.flopez.core.presentation.reducer.Reducer

@Immutable
data class CharacterDetailViewState(
    val character: Character? = null,
    val isLoading: Boolean = true,
    val error: MortyException? = null
) : Reducer.ViewState

