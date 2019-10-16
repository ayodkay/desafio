package com.ayodkay.app.fulllabdesfio.database.search

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SearchDao {

    @Query("SELECT * FROM category_table")
    fun getAll(): LiveData<List<Search>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(search: Search)

    @Delete
    suspend fun delete(search: Search)

}