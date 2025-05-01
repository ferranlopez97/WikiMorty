package com.flopez.core.database.datasource.location

import com.flopez.core.database.entity.LocationEntity

interface LocationsLocalDataSource {
    fun insert(location: LocationEntity)
}