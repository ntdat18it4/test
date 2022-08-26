package com.thaonx.mockt3h.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thaonx.mockt3h.utils.Constants


@Entity(tableName = Constants.TABLE_NAME)
data class ArticlesFavorite(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.ID_COLUMN)
    var id: Int = 0,
    @ColumnInfo(name = Constants.NAME_COLUMN)
    var name: String,
    @ColumnInfo(name = Constants.TITLE_COLUMN)
    var title: String,
    @ColumnInfo(name = Constants.IMAGE_COLUMN)
    var urlToImage: String,
    @ColumnInfo(name = Constants.DATE_COLUMN)
    var publishedAt: String,
    @ColumnInfo(name = Constants.URL_COLUMN)
    var url: String,
)