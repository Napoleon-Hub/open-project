package com.example.open.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals_table")
data class Animal (
    var name: String,
    var age: String,
    var color: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}