package com.example.open.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.open.database.entity.Animal
import com.example.open.repository.AnimalsRepository

class AnimalsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AnimalsRepository = AnimalsRepository(application)
    private val allAnimal: LiveData<List<Animal>> = repository.getAllAnimals()

    fun insert(animal: Animal) {
        repository.insert(animal)
    }

    fun update(animal: Animal) {
        repository.update(animal)
    }

    fun delete(animal: Animal) {
        repository.delete(animal)
    }
    fun deleteAllAnimals(){
        repository.deleteAllAnimals()
    }

    fun getAllAnimals(): LiveData<List<Animal>> {
        return allAnimal
    }
}