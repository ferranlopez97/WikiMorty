package com.flopez.core.domain.model

data class CharacterQueryResponse(
    val characterQueryInfo: CharacterQueryInfo,
    val characters: List<Character>
)
