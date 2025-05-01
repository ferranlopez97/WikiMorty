package com.flopez.core.data.mapper

import com.flopez.core.database.entity.LocationEntity
import com.flopez.core.domain.model.Location
import com.flopez.core.network.model.LocationDTO


fun LocationEntity.toDomain() : Location {
    return Location(
        name = this.name,
        url = this.url
    )
}

fun LocationDTO.toEntity() : LocationEntity {
    return LocationEntity(
        name = this.name,
        url = this.url
    )
}

