package com.example.nytimes_news_feed.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idFavorite")
    val id : String,
    @ColumnInfo(name = "titleFavorite")
    val title:String?,
    @ColumnInfo(name = "snippetFavorite")
    val snippet : String?,
    @ColumnInfo(name = "imageUrlFavorite")
    val imageUrl: String?,
    @ColumnInfo(name = "leadParagraphFavorite")
    val leadParagraph : String?,
    @ColumnInfo(name = "websiteFavorite")
    val website : String?,
    @ColumnInfo(name = "bylineFavorite")
    val byline : String?,
    @ColumnInfo(name = "pubDateFavorite")
    val pubDate : String?,
    @ColumnInfo(name = "sourceFavorite")
    val source : String?

)