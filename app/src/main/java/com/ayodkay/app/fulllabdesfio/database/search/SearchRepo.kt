package com.ayodkay.app.fulllabdesfio.database.search

import androidx.lifecycle.LiveData

class SearchRepo(private val searchDao: SearchDao) {

    val allSearch: LiveData<List<Search>> = searchDao.getAll()

    suspend fun insert(search: Search){
        searchDao.insertAll(search)
    }

    suspend fun delete(search: Search){
        searchDao.delete(search)
    }

    suspend fun nukeAllTable(){
        searchDao.nukeTable()
    }
}