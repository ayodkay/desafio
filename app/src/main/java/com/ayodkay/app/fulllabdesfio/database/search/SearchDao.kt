package com.ayodkay.app.fulllabdesfio.database.search

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SearchDao {

    @Query("SELECT * FROM search_table")
    fun getAll(): LiveData<List<Search>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(search: Search)

    @Delete
    suspend fun delete(search: Search)

    @Query("DELETE FROM search_table")
    suspend fun nukeTable()

}