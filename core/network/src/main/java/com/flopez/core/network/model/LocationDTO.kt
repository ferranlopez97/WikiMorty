package com.flopez.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationDTO(
    val name: String,
    val url: String
)
