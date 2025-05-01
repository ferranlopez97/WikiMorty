package com.flopez.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey val name: String,
    val url: String
)
