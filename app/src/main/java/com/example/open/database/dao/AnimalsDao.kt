package com.example.open.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.open.database.entity.Animals

@Dao
interface AnimalsDao {
    @Insert
    fun insert(note: Animals)

    @Update
    fun update(note: Animals)

    @Delete
    fun delete(note: Animals)

    @Query("SELECT * FROM animals_table")
    fun getAllAnimals(): LiveData<List<Animals>>
}