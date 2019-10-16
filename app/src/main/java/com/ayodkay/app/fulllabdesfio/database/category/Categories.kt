package com.ayodkay.app.fulllabdesfio.database.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Categories(@PrimaryKey
                @ColumnInfo(name = "categoryName") val categoryName: String,
                @ColumnInfo(name = "subCategoryName") val subCategoryName: ArrayList<String>)