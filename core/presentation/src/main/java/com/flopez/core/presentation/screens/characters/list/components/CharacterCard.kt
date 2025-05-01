package com.flopez.core.presentation.screens.characters.list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flopez.core.domain.model.Character
import com.flopez.core.presentation.screens.characters.components.CharacterImage
import com.flopez.core.presentation.ui.theme.WikiMortyTheme
import com.flopez.core.presentation.util.animation.buildSharedTransitionKey
import com.flopez.core.presentation.util.preview.fakeCharacter
import com.flopez.core.presentation.util.size.LocalSizeProvider


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterCard(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    character: Character
) {

    val sizeProvider = LocalSizeProvider.current

    Card(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CharacterImage(
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = character.buildSharedTransitionKey()),
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                    .size(sizeProvider.characterListImageSize),
                character = character
            )

            CharacterSummarizedInfo(
                modifier = Modifier
                    .height(sizeProvider.characterListImageSize)
                    .padding(sizeProvider.characterSummaryPadding),
                animatedVisibilityScope = animatedVisibilityScope,
                character = character
            )
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun CharacterCardPreview() {
    WikiMortyTheme {
        SharedTransitionLayout {
            AnimatedVisibility(
                visible = true
            ) {
                Scaffold {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                            .padding(16.dp),
                    ) {
                        CharacterCard(
                            animatedVisibilityScope = this@AnimatedVisibility,
                            modifier = Modifier.fillMaxWidth(),
                            character = fakeCharacter
                        )

                    }
                }
            }

        }
    }
}


