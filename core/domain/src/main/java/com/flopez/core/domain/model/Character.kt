package com.flopez.core.domain.model

import java.util.Date
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class Character (
    val id: Long,
    val name: String,
    val characterStatus: CharacterStatus,
    val species: String,
    val type: String,
    val characterGender: CharacterGender,
    val origin: Location?,
    val location: Location?,
    val image: String,
//    val episode: List<String>,
    val url: String,
    val created: Date
)
