package com.example.nytimes_news_feed.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val newsId: String,
    val prevKey: Int?,
    val nextKey: Int?
)