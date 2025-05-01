package com.flopez.core.presentation.screens.characters.list

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flopez.core.domain.model.Character
import com.flopez.core.presentation.R
import com.flopez.core.presentation.mapper.toUIText
import com.flopez.core.presentation.screens.characters.list.components.CharacterListTopBar
import com.flopez.core.presentation.screens.characters.list.components.CharactersList
import com.flopez.core.presentation.screens.characters.list.components.EmptyCharactersView
import com.flopez.core.presentation.screens.characters.list.components.LoadingCharactersView
import com.flopez.core.presentation.screens.characters.list.model.CharacterListViewEffect
import com.flopez.core.presentation.screens.characters.list.model.CharacterListViewIntent
import com.flopez.core.presentation.screens.characters.list.model.CharactersListViewState
import kotlinx.coroutines.launch


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onCharacterClicked: (Character) -> Unit
) {
    val viewmodel = hiltViewModel<CharacterListViewModel>()
    val state by viewmodel.state.collectAsStateWithLifecycle()
    val effect = viewmodel.effect

    val context = LocalContext.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        effect.collect { action ->
            when (action) {
                is CharacterListViewEffect.ScrollToTop -> {
                    coroutineScope.launch {
                        listState.requestScrollToItem(0)
                    }
                }

                is CharacterListViewEffect.ShowErrorToast -> {
                    Toast.makeText(context, action.exception.toUIText().asString(context), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    CharacterListView(
        animatedVisibilityScope = animatedVisibilityScope,
        state = state,
        handleIntent = viewmodel::handleIntent,
        listState = listState,
        onCharacterClicked = onCharacterClicked,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterListView(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    state: CharactersListViewState,
    listState: LazyListState,
    onCharacterClicked: (Character) -> Unit,
    handleIntent: (CharacterListViewIntent) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val isSearching = state.isSearchBarVisible


    //Si esta el buscador visible y se hace click al boton atras del dispositivo, primero cerrar la busqueda.
    BackHandler(
        state.isSearchBarVisible
    ) {
        keyboardController?.hide()
        handleIntent(CharacterListViewIntent.ToggleSearchBar)
        handleIntent(CharacterListViewIntent.ClearFilter)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    CharacterListTopBar( state.charactersFilter.name, isSearching) {
                        handleIntent(CharacterListViewIntent.FilterQueryChanged(it))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        handleIntent(CharacterListViewIntent.ToggleSearchBar)
                        handleIntent(CharacterListViewIntent.ClearFilter)
                    }) {
                        Icon(
                            imageVector = if (isSearching) Icons.Default.Close else Icons.Default.Search,
                            contentDescription =
                                if (isSearching) stringResource(R.string.close_search)
                                else stringResource(R.string.search)
                        )
                    }
                }
            )
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Crossfade(state.isLoading, animationSpec = tween(durationMillis = 300)) { isLoading ->
                if (isLoading) {
                    LoadingCharactersView(
                        modifier.fillMaxSize()
                    )
                } else {
                    if (state.characters.isEmpty()) {
                        EmptyCharactersView(
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        CharactersList(
                            listState = listState,
                            animatedVisibilityScope = animatedVisibilityScope,
                            characters = state.characters,
                            onCharacterClicked = {
                                onCharacterClicked(it)
                            },
                            onLoadMoreCharacters = {
                                handleIntent(CharacterListViewIntent.LoadMoreCharacters)
                            }
                        )
                    }
                }
            }



        }
    }
}