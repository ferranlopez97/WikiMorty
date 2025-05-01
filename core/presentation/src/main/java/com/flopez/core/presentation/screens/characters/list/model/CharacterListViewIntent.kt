package com.flopez.core.presentation.screens.characters.list.model

import androidx.compose.runtime.Immutable
import com.flopez.core.presentation.reducer.Reducer

@Immutable
sealed class CharacterListViewIntent : Reducer.ViewIntent {
    object LoadMoreCharacters : CharacterListViewIntent()
    data class FilterQueryChanged(val query: String) : CharacterListViewIntent()
    data object ClearFilter : CharacterListViewIntent()
    data object ToggleSearchBar : CharacterListViewIntent()
}
