package com.flopez.core.presentation.screens.characters.list.reducer


import com.flopez.core.presentation.reducer.Reducer
import com.flopez.core.presentation.screens.characters.list.model.CharacterListViewEffect
import com.flopez.core.presentation.screens.characters.list.model.CharacterListViewIntent
import com.flopez.core.presentation.screens.characters.list.model.CharactersListViewState

class CharactersListReducer : Reducer<CharactersListViewState, CharacterListViewIntent, CharacterListViewEffect> {

    override fun reduce(previousState: CharactersListViewState, event: CharacterListViewIntent): Pair<CharactersListViewState, CharacterListViewEffect?> {

        //Comentar
        return when (event) {
            else -> previousState to null
        }
    }
}