package com.example.nytimes_news_feed.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity
import com.example.nytimes_news_feed.core.data.source.local.entity.NewsEntity
import com.example.nytimes_news_feed.core.data.source.local.entity.RemoteKeys

@Database(
    entities = [NewsEntity::class, RemoteKeys::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun provideNewsDao(): NewsDao

    abstract fun provideRemoteKeys(): RemoteKeysDao

    abstract fun provideFavoriteDao(): FavoriteDao
}