package com.flopez.core.presentation.screens.characters.list.model

import androidx.compose.runtime.Immutable
import com.flopez.core.domain.model.Character
import com.flopez.core.domain.model.QueryFilter
import com.flopez.core.presentation.reducer.Reducer

@Immutable
data class CharactersListViewState(
    val isLoading: Boolean = true,
    val isSearchBarVisible: Boolean = false,
    val characters: List<Character> = emptyList(),
    val charactersFilter: QueryFilter.ByName = QueryFilter.ByName(name = ""), //Por defecto no filtrar nada
) : Reducer.ViewState

