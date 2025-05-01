package com.flopez.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterQueryResponseDTO(
    @SerialName("info") val characterQueryInfoDTO: CharacterQueryInfoDTO,
    @SerialName("results") val characters : List<CharacterDTO>
)
