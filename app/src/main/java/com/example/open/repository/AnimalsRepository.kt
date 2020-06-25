package com.example.open.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.open.database.dao.AnimalsDao
import com.example.open.database.data.AnimalDatabase
import com.example.open.database.entity.Animals

class AnimalsRepository(application: Application) {
    private var noteDao: AnimalsDao
    private var allAnimals: LiveData<List<Animals>>

    init {
        val newDatabase: AnimalDatabase? = AnimalDatabase.getInstance(application)
        noteDao = newDatabase!!.animalsDao()
        allAnimals = noteDao.getAllAnimals()
    }

    fun insert(animals: Animals) {
        InsertAnimalAsyncTask(noteDao).execute(animals)
    }

    fun update(animals: Animals) {
        UpdateAnimalAsyncTask(noteDao).execute(animals)
    }

    fun delete(animals: Animals) {
        DeleteAnimalAsyncTask(noteDao).execute(animals)
    }

    fun getAllAnimals(): LiveData<List<Animals>> {
        return allAnimals
    }

    private class InsertAnimalAsyncTask internal constructor(private val animalsDao: AnimalsDao) :
        AsyncTask<Animals, Void, Void>() {

        override fun doInBackground(vararg notes: Animals): Void? {
            animalsDao.insert(notes[0])
            return null
        }
    }

    private class UpdateAnimalAsyncTask internal constructor(private val animalsDao: AnimalsDao) :
        AsyncTask<Animals, Void, Void>() {

        override fun doInBackground(vararg notes: Animals): Void? {
            animalsDao.update(notes[0])
            return null
        }
    }

    private class DeleteAnimalAsyncTask internal constructor(private val animalsDao: AnimalsDao) :
        AsyncTask<Animals, Void, Void>() {

        override fun doInBackground(vararg notes: Animals): Void? {
            animalsDao.delete(notes[0])
            return null
        }
    }

}