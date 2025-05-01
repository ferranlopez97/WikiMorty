package com.flopez.core.data.mapper

import com.flopez.core.domain.model.CharacterGender

fun String.toCharacterGender() : CharacterGender {
    return when (this) {
        "Male" -> CharacterGender.MALE
        "Female" -> CharacterGender.FEMALE
        "Genderless" -> CharacterGender.GENDERLESS
        else -> CharacterGender.UNKNOWN
    }
}