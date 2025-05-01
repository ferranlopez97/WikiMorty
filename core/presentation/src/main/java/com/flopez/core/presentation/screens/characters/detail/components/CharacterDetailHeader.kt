package com.flopez.core.presentation.screens.characters.detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.flopez.core.domain.model.Character
import com.flopez.core.presentation.screens.characters.components.CharacterImage
import com.flopez.core.presentation.util.animation.buildSharedTransitionKey
import com.flopez.core.presentation.util.preview.fakeCharacter
import com.flopez.core.presentation.util.size.LocalSizeProvider
import com.flopez.core.presentation.util.test.characterNameTAG


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterDetailHeader(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    character: Character
) {

    val sizeProvider = LocalSizeProvider.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CharacterImage(
            modifier = Modifier
                .sharedElement(
                    state = rememberSharedContentState(key = character.buildSharedTransitionKey()),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
                .size(sizeProvider.characterDetailImageSize),
            character = character,
            useCrossFade = false
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(sizeProvider.defaultPadding)
                .testTag(characterNameTAG),
            textAlign = TextAlign.Center,
            text = character.name,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun CharacterDetailHeaderPreview() {
    SharedTransitionLayout {
        AnimatedVisibility(
            true
        ) {
            CharacterDetailHeader(
                animatedVisibilityScope = this,
                character = fakeCharacter
            )
        }
    }
}