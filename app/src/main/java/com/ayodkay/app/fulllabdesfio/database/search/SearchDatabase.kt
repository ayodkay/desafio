package com.ayodkay.app.fulllabdesfio.database.search

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Search::class], version = 1)
abstract class SearchDatabase : RoomDatabase() {

    abstract fun searchDao(): SearchDao

    companion object {
        @Volatile
        private var INSTANCE: SearchDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): SearchDatabase {
            return INSTANCE


                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        SearchDatabase::class.java,
                        "search_table"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(
                            WordDatabaseCallback(
                                scope
                            )
                        )
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch {
                        database.searchDao()
                    }
                }
            }
        }
    }

}