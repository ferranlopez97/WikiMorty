package com.flopez.core.presentation.screens.characters.list.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flopez.core.domain.model.Character
import com.flopez.core.presentation.util.list.LoadMoreCharactersEffect
import com.flopez.core.presentation.util.size.LocalSizeProvider

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharactersList(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    animatedVisibilityScope: AnimatedVisibilityScope,
    characters: List<Character>,
    onCharacterClicked: (Character) -> Unit,
    onLoadMoreCharacters: () -> Unit
) {

    val sizeProvider = LocalSizeProvider.current

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(sizeProvider.characterListSpacing),
        contentPadding = PaddingValues(horizontal = sizeProvider.characterListPadding)
    ) {
        items(
            items = characters,
            key = { it.id }
        ) { character ->
            CharacterCard(
                animatedVisibilityScope = animatedVisibilityScope,
                modifier = Modifier.clickable {
                    onCharacterClicked(character)
                },
                character = character
            )
        }
    }

    LoadMoreCharactersEffect(
        listState = listState,
        onEndReached = {onLoadMoreCharacters()}
    )
}