package com.ayodkay.app.fulllabdesfio.database.category

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Categories::class], version = 1)
@TypeConverters(Converters::class)
abstract class CategoryDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: CategoryDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): CategoryDatabase {
            return INSTANCE


                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CategoryDatabase::class.java,
                        "category_table"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(
                            WordDatabaseCallback(
                                scope
                            )
                        )
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
                        database.categoryDao()
                    }
                }
            }
        }
    }

}
