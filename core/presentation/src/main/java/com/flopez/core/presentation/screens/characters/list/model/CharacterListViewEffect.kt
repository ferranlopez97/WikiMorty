package com.flopez.core.presentation.screens.characters.list.model

import com.flopez.core.domain.exception.MortyException
import com.flopez.core.presentation.reducer.Reducer

sealed class CharacterListViewEffect : Reducer.ViewEffect {
    data object ScrollToTop : CharacterListViewEffect()
    data class ShowErrorToast(val exception: MortyException) : CharacterListViewEffect()
}