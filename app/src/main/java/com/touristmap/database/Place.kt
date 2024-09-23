package com.touristmap.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class Place (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val name: String,
    val visitOrder: Int,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val accommodationCost: Double,
    val transportationCost: Double?,
    val comments: String
)