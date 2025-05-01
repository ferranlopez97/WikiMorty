package com.flopez.core.data.generators

import com.flopez.core.database.entity.CharacterEntity
import com.flopez.core.database.entity.CharacterWithLocations
import com.flopez.core.database.entity.LocationEntity
import com.flopez.core.network.model.CharacterDTO
import com.flopez.core.network.model.CharacterQueryInfoDTO
import com.flopez.core.network.model.CharacterQueryResponseDTO
import com.flopez.core.network.model.LocationDTO


fun queryResponseDto(): CharacterQueryResponseDTO {
    return CharacterQueryResponseDTO(
        characterQueryInfo(),
        (1..20).map {
            characterDTO(it.toLong())
        }
    )
}

fun characterDTO(
    id: Long = 1
): CharacterDTO {
    return CharacterDTO(
        id = id,
        name = "Rick Sanchez",
        status = "alive",
        species = "Human",
        type = "",
        gender = "male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        url = "https://rickandmortyapi.com/api/character/1",
        created = "2017-11-04T18:48:46.250Z",
        location = LocationDTO(
            "Location1",
            ""
        ),
        origin = LocationDTO(
            "Location1",
            ""
        ),
        episode = emptyList()
    )
}

fun CharacterEntity.characterWithLocations(): CharacterWithLocations {
    return CharacterWithLocations(
        character = this,
        originLocation = LocationEntity("originLocation", ""),
        currentLocation = LocationEntity("currentLocation", "")
    )
}

fun characterQueryInfo(): CharacterQueryInfoDTO {
    return CharacterQueryInfoDTO(
        count = 820,
        pages = 1,
        next = null,
        prev = "previos_page"
    )
}