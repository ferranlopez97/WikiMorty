package com.flopez.core.data.mapper

import com.flopez.core.database.entity.CharacterEntity
import com.flopez.core.database.entity.CharacterWithLocations
import com.flopez.core.domain.model.Character
import com.flopez.core.network.model.CharacterDTO
import com.flopez.util.toDate

fun CharacterDTO.toEntity() : CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        state = this.status,
        gender = gender,
        species = species,
        type = type,
        image = image,
        url = url,
        created = this.created,
        originLocationName = this.origin.name,
        currentLocationName = this.location.name
    )
}

fun CharacterWithLocations.toDomain() : Character {
    return Character(
        id = this.character.id,
        name = this.character.name,
        characterStatus = this.character.state.toCharacterStatus(),
        characterGender = this.character.gender.toCharacterGender(),
        created = this.character.created.toDate(),
        species = this.character.species,
        type = this.character.type,
        image = this.character.image,
        url = this.character.url,
        location = this.currentLocation?.toDomain(),
        origin = this.originLocation?.toDomain()
    )
}