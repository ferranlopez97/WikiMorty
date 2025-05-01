package com.flopez.core.presentation.screens.characters.detail.model

import androidx.compose.runtime.Immutable
import com.flopez.core.presentation.reducer.Reducer


@Immutable
sealed class CharacterDetailViewIntent : Reducer.ViewIntent {
    data object AddToFavorites : CharacterDetailViewIntent()
}