package com.flopez.core.presentation.screens.characters.list.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationSearching
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.flopez.core.domain.model.Character
import com.flopez.core.presentation.mapper.toIcon
import com.flopez.core.presentation.mapper.toUIText
import com.flopez.core.presentation.util.string.capitalize

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterSummarizedInfo(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    character: Character
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
//                modifier = Modifier
//                    .skipToLookaheadSize()
//                    .sharedBounds(
//                        rememberSharedContentState(key = "character_name_${character.id}_${character.name}"),
//                        animatedVisibilityScope = animatedVisibilityScope,
//                    ),
                text = character.name,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Spacer(Modifier.width(8.dp))

            Icon(
                imageVector = character.characterGender.toIcon(),
                contentDescription = ""
            )
        }

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = character.species,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(" - ")
            Text(
                text = character.characterStatus.toUIText().asString().capitalize(),
                style = MaterialTheme.typography.bodyMedium
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.scale(0.7f),
                imageVector = Icons.Rounded.LocationSearching, contentDescription = ""
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = character.location?.name ?: "-",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }

    }
}