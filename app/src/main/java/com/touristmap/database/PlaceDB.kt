package com.touristmap.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Place::class], version = 1)
abstract class PlaceDB: RoomDatabase() {
    abstract fun placeDao(): PlaceDao

    companion object {
        @Volatile
        private var INTANCE: PlaceDB? = null

        fun getDatabase(context:Context): PlaceDB {
            return INTANCE ?: synchronized(this) {
                INTANCE ?: buildDatabase(context).also { INTANCE = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, PlaceDB::class.java, "place_db")
                .fallbackToDestructiveMigration()
                .build()
    }
}