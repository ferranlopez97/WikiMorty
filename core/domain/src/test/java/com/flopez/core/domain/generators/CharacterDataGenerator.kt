package com.flopez.core.domain.generators

import com.flopez.core.domain.model.Character
import com.flopez.core.domain.model.CharacterGender
import com.flopez.core.domain.model.CharacterStatus
import com.flopez.core.domain.model.Location
import java.util.Date


fun character() : Character {
    return Character(
        id = 1,
        name = "Rick Sanchez",
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