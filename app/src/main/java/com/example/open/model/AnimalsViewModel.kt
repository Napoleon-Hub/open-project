package com.example.open.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.open.database.entity.Animals
import com.example.open.repository.AnimalsRepository

class AnimalsViewModel(application: Application) : AndroidViewModel(application){
    private var repository : AnimalsRepository = AnimalsRepository(application)
    private var allAnimals : LiveData<List<Animals>> = repository.getAllAnimals()

    fun insert(note : Animals){
        repository.insert(note)
    }
    fun update(note : Animals){
        repository.update(note)
    }
    fun delete(note : Animals){
        repository.delete(note)
    }
    fun getAllAnimals() : LiveData<List<Animals>> {
        return allAnimals
    }
}