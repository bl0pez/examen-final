package com.touristmap.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(place: Place)

    @Update
    suspend fun update(place: Place)

    @Delete
    suspend fun delete(vararg place: Place)

    @Query("SELECT * FROM places ORDER BY visitOrder")
    fun findAll(): LiveData<List<Place>>

    @Query("SELECT * FROM places WHERE uid = :placeId LIMIT 1")
    suspend fun findPlaceById(placeId: Int): Place?


}