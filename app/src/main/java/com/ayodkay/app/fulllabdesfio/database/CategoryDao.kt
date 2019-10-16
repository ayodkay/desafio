package com.ayodkay.app.fulllabdesfio.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table")
    fun getAll(): LiveData<List<Categories>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(cm: Categories)

    @Delete
    suspend fun delete(cm: Categories)

}