package com.flopez.core.data.generators

import com.flopez.core.database.entity.CharacterEntity


fun characterEntity(
    id: Long = 1L,
    name: String
) : CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        state = "Alive",
        gender = "Male",
        species = "",
        type = "",
        image = "",
        url = "",
        originLocationName = "origin",
        currentLocationName = "current",
        created = "2017-11-04T22:34:53.659Z"
    )
}