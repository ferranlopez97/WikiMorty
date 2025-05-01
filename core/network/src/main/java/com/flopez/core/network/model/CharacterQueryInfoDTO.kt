package com.flopez.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterQueryInfoDTO(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)