package com.example.nytimes_news_feed.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id : String,
    @ColumnInfo(name = "title")
    val title:String?,
    @ColumnInfo(name = "snippet")
    val snippet : String?,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,
    @ColumnInfo(name = "leadParagraph")
    val leadParagraph : String?,
    @ColumnInfo(name = "website")
    val website : String?,
    @ColumnInfo(name = "byline")
    val byline : String?,
    @ColumnInfo(name = "pubDate")
    val pubDate : String?,
    @ColumnInfo(name = "source")
    val source : String?

)