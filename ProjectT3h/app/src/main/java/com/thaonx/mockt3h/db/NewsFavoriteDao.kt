package com.thaonx.mockt3h.db

import androidx.room.*
import com.thaonx.mockt3h.model.ArticlesFavorite
import com.thaonx.mockt3h.utils.Constants
import kotlinx.coroutines.flow.Flow


@Dao
interface NewsFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleFavorite(articlesFavorite: ArticlesFavorite)

    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
    fun getAllArticleFavorite(): Flow<List<ArticlesFavorite>>

    @Delete
    suspend fun deleteArticleFavorite(articlesFavorite: ArticlesFavorite)
}