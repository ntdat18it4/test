package com.thaonx.mockt3h.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thaonx.mockt3h.model.ArticlesFavorite
import com.thaonx.mockt3h.utils.Constants


@Database(entities = [ArticlesFavorite::class], version = 1, exportSchema = true)
abstract class NewsFavoriteDatabase : RoomDatabase() {

    abstract fun getNewsFavoriteDao(): NewsFavoriteDao

    companion object {

        @Volatile
        private var INSTANCE: NewsFavoriteDatabase? = null

        fun getInstance(context: Context): NewsFavoriteDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                NewsFavoriteDatabase::class.java,
                Constants.DATABASE_NAME).build()
                .also {
                    INSTANCE = it
                }
        }
    }
}