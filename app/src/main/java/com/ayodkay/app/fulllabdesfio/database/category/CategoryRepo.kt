package com.ayodkay.app.fulllabdesfio.database.category

import androidx.lifecycle.LiveData
import com.ayodkay.app.fulllabdesfio.database.category.Categories
import com.ayodkay.app.fulllabdesfio.database.category.CategoryDao

class CategoryRepo(private val categoryDao: CategoryDao) {

    val allCategory: LiveData<List<Categories>> = categoryDao.getAll()

    suspend fun insert(category: Categories){
        categoryDao.insertAll(category)
    }

    suspend fun delete(category: Categories){
        categoryDao.delete(category)
    }
}
