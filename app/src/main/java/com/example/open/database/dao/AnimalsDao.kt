package com.example.open.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.open.database.entity.Animal

@Dao
interface AnimalsDao {
    @Insert
    fun insert(note: Animal)

    @Update
    fun update(note: Animal)

    @Delete
    fun delete(note: Animal)

    @Query("DELETE FROM animals_table")
    fun deleteAllAnimals()

    @Query("SELECT * FROM animals_table")
    fun getAllAnimals(): LiveData<List<Animal>>
}