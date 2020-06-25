package com.example.open.database.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.open.database.dao.AnimalsDao
import com.example.open.database.entity.Animals

@Database(entities = [Animals::class], version = 1)
abstract class AnimalDatabase : RoomDatabase() {

    abstract fun animalsDao(): AnimalsDao

    companion object {
        private var instance: AnimalDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AnimalDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimalDatabase::class.java, "animals_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}