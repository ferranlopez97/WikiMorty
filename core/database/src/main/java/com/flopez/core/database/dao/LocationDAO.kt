package com.flopez.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.flopez.core.database.entity.LocationEntity

@Dao
interface LocationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(location: LocationEntity)
}