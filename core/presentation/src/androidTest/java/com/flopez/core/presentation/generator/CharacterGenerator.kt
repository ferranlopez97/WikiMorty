package com.flopez.core.presentation.generator

import com.flopez.core.domain.model.Character
import com.flopez.core.domain.model.CharacterGender
import com.flopez.core.domain.model.CharacterStatus
import com.flopez.core.domain.model.Location
import java.util.Date


fun character(
    id: Long,
    name: String
) : Character {
    return Character(
        id = id,
        name = name,
        characterStatus = CharacterStatus.ALIVE,
        species = "Human",
        type = "",
        characterGender = CharacterGender.MALE,
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        url = "https://rickandmortyapi.com/api/character/1",
        created = Date(),
        location = Location(
            "Location1",
            ""
        ),
        origin = Location(
            "Location1",
            ""
        )
    )
}


fun fakeCharacters() : List<Character> {
    return (1 .. 20).map {
        character(id = it.toLong(), "Personaje_$it")
    }
}