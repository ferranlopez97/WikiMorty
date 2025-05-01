package com.flopez.core.presentation.screens.characters.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.flopez.core.presentation.R
import com.flopez.core.presentation.util.size.LocalSizeProvider

@Composable
fun CharacterDetailBottomBar(
    onAddToFavoritesClicked : () -> Unit
) {

    val sizeProvider = LocalSizeProvider.current

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(sizeProvider.defaultPadding),
            horizontalArrangement = Arrangement.spacedBy(sizeProvider.defaultSpacing)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                shape = ShapeDefaults.Medium,
                onClick = onAddToFavoritesClicked
            ) {
                Text(stringResource(R.string.add_to_favorites), color = MaterialTheme.colorScheme.onSurface)
            }

            Button(
                shape = ShapeDefaults.Medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ),
                onClick = {
                    //Aqui llamaria intent para compartir personaje
                }
            ) {
                Icon(Icons.Rounded.Share, stringResource(R.string.share))
            }
        }
    }
}