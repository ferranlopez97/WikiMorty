package com.flopez.core.domain.model

data class CharacterQueryInfo(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)
