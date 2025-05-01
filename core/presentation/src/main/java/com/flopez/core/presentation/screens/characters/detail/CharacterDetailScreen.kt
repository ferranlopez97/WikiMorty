package com.flopez.core.presentation.screens.characters.detail

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flopez.core.presentation.R
import com.flopez.core.presentation.components.loaders.ProgressIndicator
import com.flopez.core.presentation.mapper.toUIText
import com.flopez.core.presentation.screens.characters.detail.components.CharacterDetailBottomBar
import com.flopez.core.presentation.screens.characters.detail.components.CharacterDetailHeader
import com.flopez.core.presentation.screens.characters.detail.components.CharacterDetailInfo
import com.flopez.core.presentation.screens.characters.detail.components.CharacterDetailTopBar
import com.flopez.core.presentation.screens.characters.detail.components.CharacterLoadError
import com.flopez.core.presentation.screens.characters.detail.model.CharacterDetailViewEffect
import com.flopez.core.presentation.screens.characters.detail.model.CharacterDetailViewIntent
import com.flopez.core.presentation.screens.characters.detail.model.CharacterDetailViewState
import com.flopez.core.presentation.util.size.LocalSizeProvider
import com.flopez.core.presentation.util.string.capitalize
import com.flopez.core.presentation.util.test.circularProgressIndicatorTAG

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterDetailScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBack: () -> Unit
) {

    val viewModel = hiltViewModel<CharacterDetailViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(effect) {
        effect.collect { action ->
            when (action) {
                is CharacterDetailViewEffect.AddedToFav -> {
                    Toast.makeText(context, R.string.added_to_favs, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    CharacterDetailView(
        onBack = onBack,
        animatedVisibilityScope = animatedVisibilityScope,
        state = state,
        handleIntent = viewModel::handleIntent
    )
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterDetailView(
    onBack: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    state: CharacterDetailViewState,
    handleIntent: (CharacterDetailViewIntent) -> Unit
) {

    val sizeProvider = LocalSizeProvider.current

    Scaffold(
        topBar = {
            CharacterDetailTopBar(
                title = state.character?.name ?: "",
                onBackPressed = onBack
            )
        },
        bottomBar = {
            if (state.error == null) {
                CharacterDetailBottomBar {
                    handleIntent(CharacterDetailViewIntent.AddToFavorites)
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(sizeProvider.defaultPadding),
            verticalArrangement = Arrangement.Center,
        ) {

            if (state.error != null) {
                CharacterLoadError(
                    state.error.toUIText().asString().capitalize(), onBack
                )
            } else if (state.isLoading) {
                ProgressIndicator(
                    modifier = Modifier.testTag(circularProgressIndicatorTAG)
                )
            } else {
                state.character?.let { character ->
                    CharacterDetailHeader(
                        modifier = Modifier.fillMaxWidth(),
                        animatedVisibilityScope = animatedVisibilityScope,
                        character = character
                    )

                    CharacterDetailInfo(
                        character = character
                    )
                }
            }
        }
    }
}
