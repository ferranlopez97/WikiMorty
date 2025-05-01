package com.flopez.core.presentation.screens.characters.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.flopez.core.domain.exception.CharacterQueryException
import com.flopez.core.domain.result.onFailure
import com.flopez.core.domain.result.onSuccess
import com.flopez.core.domain.usecase.GetCharacterByIdUseCase
import com.flopez.core.presentation.screens.characters.detail.model.CharacterDetailViewEffect
import com.flopez.core.presentation.screens.characters.detail.model.CharacterDetailViewIntent
import com.flopez.core.presentation.screens.characters.detail.model.CharacterDetailViewState
import com.flopez.core.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : BaseViewModel<CharacterDetailViewState, CharacterDetailViewIntent, CharacterDetailViewEffect>(
    initialState = CharacterDetailViewState(),
) {

    private val CHARACTER_ID_KEY = "id"

    init {
        viewModelScope.launch {
            queryCharacter()
        }
    }

    override fun handleIntent(intent: CharacterDetailViewIntent) {
        //Aqui deberia llamar reducer. Comentar
        when (intent) {
            is CharacterDetailViewIntent.AddToFavorites -> {
                sendEffect(CharacterDetailViewEffect.AddedToFav)
            }
        }
    }


    private fun queryCharacter() {
        viewModelScope.launch {
            val id = savedStateHandle.get<Long>(CHARACTER_ID_KEY)

            id?.let {
                getCharacterByIdUseCase.execute(it)
                    .onSuccess { result ->
                        _state.update { prevState ->
                            prevState.copy(
                                isLoading = false,
                                character = result
                            )
                        }
                    }
                    .onFailure { exception ->
                        _state.update { prevState ->
                            prevState.copy(
                                isLoading = false,
                                error = exception
                            )
                        }
                    }
            } ?: run {
                _state.update { prevState ->
                    prevState.copy(
                        isLoading = false,
                        error = CharacterQueryException.InvalidCharacterId
                    )
                }
            }
        }

    }
}