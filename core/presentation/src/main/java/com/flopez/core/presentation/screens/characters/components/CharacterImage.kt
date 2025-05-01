package com.flopez.core.presentation.screens.characters.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.flopez.core.domain.model.Character
import com.flopez.core.presentation.R
import com.flopez.core.presentation.components.loaders.ProgressIndicator

const val CROSSFADE = 500

@Composable
fun CharacterImage(
    modifier: Modifier = Modifier,
    character: Character,
    useCrossFade: Boolean = true
) {

    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(character.image)
            .also {
                if(useCrossFade) it.crossfade(CROSSFADE)
            }
            .build(),
        contentDescription = character.name,

        error = {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.image_placeholder),
                contentDescription = stringResource(R.string.image_placeholder)
            )
        },
        loading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ProgressIndicator()
            }
        },
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(ShapeDefaults.Medium)
    )
}