package com.example.open.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals_table")
data class Animals(
    var name: String,
    var age: String,
    var color: String
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Animals

        if (name != other.name) return false
        if (age != other.age) return false
        if (color != other.color) return false
        if (id != other.id) return false

        return true
    }
}