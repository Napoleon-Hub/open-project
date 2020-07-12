package com.example.open.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.open.database.dao.AnimalsDao
import com.example.open.database.data.AnimalDatabase
import com.example.open.database.entity.Animal

class AnimalsRepository(application: Application) {
    private var animalsDao: AnimalsDao
    private var allAnimal: LiveData<List<Animal>>

    init {
        val newDatabase: AnimalDatabase? = AnimalDatabase.getInstance(application)
        animalsDao = newDatabase!!.animalsDao()
        allAnimal = animalsDao.getAllAnimals()
    }

    fun insert(animal: Animal) {
        InsertAnimalAsyncTask(animalsDao).execute(animal)
    }

    fun update(animal: Animal) {
        UpdateAnimalAsyncTask(animalsDao).execute(animal)
    }

    fun delete(animal: Animal) {
        DeleteAnimalAsyncTask(animalsDao).execute(animal)
    }

    fun getAllAnimals(): LiveData<List<Animal>> {
        return allAnimal
    }

    fun deleteAllAnimals(){
        DeleteAllAnimalsAsyncTask(animalsDao).execute()
    }

    private class InsertAnimalAsyncTask internal constructor(private val animalsDao: AnimalsDao) :
        AsyncTask<Animal, Void, Void>() {

        override fun doInBackground(vararg notes: Animal): Void? {
            animalsDao.insert(notes[0])
            return null
        }
    }

    private class UpdateAnimalAsyncTask internal constructor(private val animalsDao: AnimalsDao) :
        AsyncTask<Animal, Void, Void>() {

        override fun doInBackground(vararg notes: Animal): Void? {
            animalsDao.update(notes[0])
            return null
        }
    }

    private class DeleteAnimalAsyncTask internal constructor(private val animalsDao: AnimalsDao) :
        AsyncTask<Animal, Void, Void>() {

        override fun doInBackground(vararg notes: Animal): Void? {
            animalsDao.delete(notes[0])
            return null
        }
    }

    private class DeleteAllAnimalsAsyncTask internal constructor(private val animalsDao: AnimalsDao) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            animalsDao.deleteAllAnimals()
            return null
        }
    }

}