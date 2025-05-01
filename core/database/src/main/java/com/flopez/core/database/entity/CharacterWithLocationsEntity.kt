package com.flopez.core.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithLocations(
    @Embedded val character: CharacterEntity,

    @Relation(
        parentColumn = "originLocationName",
        entityColumn = "name"
    )
    val originLocation: LocationEntity?,

    @Relation(
        parentColumn = "currentLocationName",
        entityColumn = "name"
    )
    val currentLocation: LocationEntity?
)