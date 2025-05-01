package com.flopez.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(

    @PrimaryKey
    val id: Long,
    val name: String,
    val state: String,
    val gender: String,
    val species: String,
    val type: String,
    val image: String,
    val url: String,
    val created: String,
    val originLocationName: String,
    val currentLocationName: String
)
