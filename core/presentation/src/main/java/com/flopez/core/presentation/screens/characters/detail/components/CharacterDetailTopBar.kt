package com.flopez.core.presentation.screens.characters.detail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.flopez.core.presentation.R
import com.flopez.core.presentation.util.test.characterTitleTAG

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailTopBar(
    title: String,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.testTag(characterTitleTAG),
                style = MaterialTheme.typography.headlineMedium,
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        },
        navigationIcon = {
            IconButton(onBackPressed) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.go_back)
                )
            }
        }
    )
}