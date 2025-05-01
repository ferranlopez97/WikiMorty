package com.flopez.core.presentation.screens.characters.list

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.flopez.core.domain.model.QueryFilter
import com.flopez.core.domain.repository.CharactersRepository
import com.flopez.core.domain.result.onFailure
import com.flopez.core.domain.result.onSuccess
import com.flopez.core.domain.usecase.QueryPagedCharactersUseCase
import com.flopez.core.presentation.screens.characters.list.model.CharacterListViewEffect
import com.flopez.core.presentation.screens.characters.list.model.CharacterListViewIntent
import com.flopez.core.presentation.screens.characters.list.model.CharactersListViewState
import com.flopez.core.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val queryPagedCharactersUseCase: QueryPagedCharactersUseCase,
) : BaseViewModel<CharactersListViewState, CharacterListViewIntent, CharacterListViewEffect>(
    initialState = CharactersListViewState(),
) {

    private val TAG = this::class.simpleName

    private val SEARCH_QUERY_DEBOUNCE_DURATION = 500L

    //Flow separado para la query, asi puedo hacer debounce. En la UI se muestra el texto del state, no de este flow.
    private val _queryFilter: MutableStateFlow<QueryFilter> = MutableStateFlow(QueryFilter.ByName(""))


    val charactersFlow = _queryFilter
        .debounce(SEARCH_QUERY_DEBOUNCE_DURATION)
        .flatMapLatest { filter ->
            charactersRepository.getAllCharacters(filter)
        }

    override val state = combine(
        _state,
        charactersFlow,
    ) { state, characters ->
        state.copy(
            isLoading = false,
            characters = characters,
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CharactersListViewState()
        )


    init {
        //Hacer la query al instanciar viewmodel
        queryCharacters()
    }


    override fun handleIntent(intent: CharacterListViewIntent) {
        println("intent$intent")
        when (intent) {
            CharacterListViewIntent.LoadMoreCharacters -> queryCharacters()
            is CharacterListViewIntent.FilterQueryChanged -> updateSearchQuery(intent.query)
            CharacterListViewIntent.ClearFilter -> updateSearchQuery("")
            is CharacterListViewIntent.ToggleSearchBar -> toggleSearchBarVisibility()
        }
    }

    private fun toggleSearchBarVisibility() {
        _state.update {
            it.copy(
                isSearchBarVisible = !it.isSearchBarVisible
            )
        }
    }

    private fun updateSearchQuery(query: String) {
        //Actualizar estado de la UI
        _state.update {
            it.copy(
                charactersFilter = QueryFilter.ByName(query)
            )
        }

        //Actualizar valor del flow para poder hacer debounce
        _queryFilter.update {
            QueryFilter.ByName(query)
        }

        sendEffect(CharacterListViewEffect.ScrollToTop)
    }

    private fun queryCharacters() {
        viewModelScope.launch {
            queryPagedCharactersUseCase.execute(_state.value.charactersFilter)
                .onSuccess {
                    println("queryCharacters success")

                    Log.d(TAG, "queryCharacters: success")
                }
                .onFailure { exception ->
                    println("queryCharacters error")
                    Log.d(TAG, "queryCharacters: error $exception")
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    sendEffect(CharacterListViewEffect.ShowErrorToast(exception))
                }
        }
    }

}