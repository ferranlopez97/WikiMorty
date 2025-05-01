package com.flopez.core.presentation.mapper

import com.flopez.core.domain.exception.CharacterQueryException
import com.flopez.core.domain.exception.MortyException
import com.flopez.core.presentation.R
import com.flopez.core.presentation.components.text.UIText

fun MortyException.toUIText(): UIText {
    return when (this) {
        is CharacterQueryException -> this.toUIText()
        else -> UIText.StringResource(R.string.something_went_wrong)
    }
}


fun CharacterQueryException.toUIText(): UIText {
    return when (this) {
        CharacterQueryException.QueryCharactersListException -> UIText.StringResource(R.string.error_while_querying_characters)
        is CharacterQueryException.QuerySingleCharacterException -> UIText.StringResource(R.string.error_loading_character)
        CharacterQueryException.InvalidCharacterId -> UIText.StringResource(R.string.error_loading_character)
    }
}

