package com.flopez.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDTO(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationDTO,
    val location: LocationDTO,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)
