package com.flopez.core.database.datasource.location

import com.flopez.core.database.dao.LocationDAO
import com.flopez.core.database.entity.LocationEntity
import javax.inject.Inject

class LocationRoomDataSource @Inject constructor(
    private val locationDAO: LocationDAO
) : LocationsLocalDataSource {

    override fun insert(location: LocationEntity) = locationDAO.insert(location)
}