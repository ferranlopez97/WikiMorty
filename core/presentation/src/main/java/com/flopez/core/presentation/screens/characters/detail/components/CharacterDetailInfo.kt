package com.flopez.core.presentation.screens.characters.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.flopez.core.domain.model.Character
import com.flopez.core.presentation.R
import com.flopez.core.presentation.mapper.toIcon
import com.flopez.core.presentation.mapper.toUIText
import com.flopez.core.presentation.util.string.capitalize
import com.flopez.util.toStringFormat

@Composable
fun CharacterDetailInfo(modifier: Modifier = Modifier, character: Character) {

    Column(
        modifier = modifier
    ) {

        CharacterAttribute(
            attributeTitle = stringResource(R.string.status),
            attributeContent = {
                CharacterAttributeText(character.characterStatus.toUIText().asString())
            }
        )

        CharacterAttribute(
            attributeTitle = stringResource(R.string.gender),
            attributeContent = {
                Row {
                    CharacterAttributeText(character.characterGender.toUIText().asString().capitalize())
                    Icon(character.characterGender.toIcon(), character.characterGender.toUIText().asString())
                }
            }
        )

        CharacterAttribute(
            attributeTitle = stringResource(R.string.species),
            attributeContent = {
                CharacterAttributeText(character.species)
            }
        )

        CharacterAttribute(
            attributeTitle = stringResource(R.string.type),
            attributeContent = {
                val text = if (character.type.isNotEmpty()) character.type.capitalize() else "-"
                CharacterAttributeText(text)
            }
        )

        CharacterAttribute(
            attributeTitle = stringResource(R.string.origin),
            attributeContent = {
                CharacterAttributeText(character.origin?.name ?: "-")
            }
        )

        CharacterAttribute(
            attributeTitle = stringResource(R.string.current_location),
            attributeContent = {
                CharacterAttributeText(character.location?.name ?: "-")
            }
        )

        CharacterAttribute(
            attributeTitle = stringResource(R.string.first_appearence),
            attributeContent = {
                CharacterAttributeText(character.created.toStringFormat())
            }
        )
    }
}