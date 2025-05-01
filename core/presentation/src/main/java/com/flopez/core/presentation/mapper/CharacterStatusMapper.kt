package com.flopez.core.presentation.mapper

import com.flopez.core.domain.model.CharacterStatus
import com.flopez.core.presentation.R
import com.flopez.core.presentation.components.text.UIText


fun CharacterStatus.toUIText() : UIText {
    return when(this) {
        CharacterStatus.DEAD -> UIText.StringResource(R.string.status_dead)
        CharacterStatus.ALIVE -> UIText.StringResource(R.string.status_alive)
        CharacterStatus.UNKNOWN -> UIText.StringResource(R.string.status_unknown)
    }
}