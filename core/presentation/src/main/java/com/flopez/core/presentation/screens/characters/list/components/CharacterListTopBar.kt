package com.flopez.core.presentation.screens.characters.list.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.flopez.core.presentation.R
import com.flopez.core.presentation.components.input.TextFieldWithPlaceholder

@Composable
fun CharacterListTopBar(
    query: String,
    isSearching: Boolean,
    onQueryChanged: (String) -> Unit,
) {
    if (isSearching) {
        TextFieldWithPlaceholder(query, onQueryChanged)
    } else {
        Text(stringResource(R.string.wiki_morty), style = MaterialTheme.typography.headlineMedium)
    }
}