package com.touristmap.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(place: Place)

    @Query("SELECT * FROM places ORDER BY visitOrder")
    fun findAll(): LiveData<List<Place>>
}