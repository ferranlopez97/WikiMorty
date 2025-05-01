package com.flopez.core.data.mapper

import com.flopez.core.domain.model.CharacterStatus


fun String.toCharacterStatus() : CharacterStatus {
    return when (this) {
        "Alive" -> CharacterStatus.ALIVE
        "Dead" -> CharacterStatus.DEAD
        else -> CharacterStatus.UNKNOWN
    }
}