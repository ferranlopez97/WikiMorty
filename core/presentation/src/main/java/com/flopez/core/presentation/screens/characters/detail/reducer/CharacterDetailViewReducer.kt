package com.flopez.core.presentation.screens.characters.detail.reducer

import com.flopez.core.presentation.reducer.Reducer
import com.flopez.core.presentation.screens.characters.detail.model.CharacterDetailViewEffect
import com.flopez.core.presentation.screens.characters.detail.model.CharacterDetailViewIntent
import com.flopez.core.presentation.screens.characters.detail.model.CharacterDetailViewState

class CharacterDetailViewReducer : Reducer<CharacterDetailViewState, CharacterDetailViewIntent, CharacterDetailViewEffect>{

    override fun reduce(previousState: CharacterDetailViewState, event: CharacterDetailViewIntent): Pair<CharacterDetailViewState, CharacterDetailViewEffect?> {
        return when (event) {
            is CharacterDetailViewIntent.AddToFavorites -> previousState to CharacterDetailViewEffect.AddedToFav
        }
    }

}