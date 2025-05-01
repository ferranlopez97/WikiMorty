package com.flopez.core.presentation.util.animation

import com.flopez.core.domain.model.Character


fun Character.buildSharedTransitionKey() : String = "character_${this.id}_${this.name}"