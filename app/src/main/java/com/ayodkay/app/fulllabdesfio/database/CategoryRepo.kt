package com.ayodkay.app.fulllabdesfio.database

import androidx.lifecycle.LiveData

class CategoryRepo(private val categoryDao: CategoryDao) {

    val allCategory: LiveData<List<Categories>> = categoryDao.getAll()

    suspend fun insert(url: Categories){
        categoryDao.insertAll(url)
    }

    suspend fun delete(url: Categories){
        categoryDao.delete(url)
    }
}
