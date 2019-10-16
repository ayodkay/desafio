package com.ayodkay.app.fulllabdesfio.database.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Search(@PrimaryKey
                      @ColumnInfo(name = "productName") val productName: String,
                      @ColumnInfo(name = "productPrice") val productPrice: String,
                      @ColumnInfo(name = "productImage") val productImage: String,
                      @ColumnInfo(name = "productPaymentOption") val productPaymentOption: String)