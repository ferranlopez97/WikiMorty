package com.flopez.core.presentation.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Female
import androidx.compose.material.icons.rounded.Male
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material.icons.rounded.Transgender
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.flopez.core.domain.model.CharacterGender
import com.flopez.core.presentation.R
import com.flopez.core.presentation.components.text.UIText


@Composable
fun CharacterGender.toIcon() : ImageVector {
    return when (this) {
        CharacterGender.FEMALE -> Icons.Rounded.Female
        CharacterGender.MALE -> Icons.Rounded.Male
        CharacterGender.GENDERLESS -> Icons.Rounded.Transgender
        CharacterGender.UNKNOWN -> Icons.Rounded.QuestionMark
    }
}


@Composable
fun CharacterGender.toUIText() : UIText {
    return when (this) {
        CharacterGender.FEMALE -> UIText.StringResource(R.string.gender_female)
        CharacterGender.MALE -> UIText.StringResource(R.string.gender_male)
        CharacterGender.GENDERLESS -> UIText.StringResource(R.string.gender_genderless)
        CharacterGender.UNKNOWN -> UIText.StringResource(R.string.gender_unknown)
    }
}