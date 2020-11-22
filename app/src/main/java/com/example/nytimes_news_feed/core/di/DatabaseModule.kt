package com.example.nytimes_news_feed.core.di
import android.content.Context
import androidx.room.Room
import com.example.nytimes_news_feed.core.data.source.local.room.FavoriteDao
import com.example.nytimes_news_feed.core.data.source.local.room.NewsDao
import com.example.nytimes_news_feed.core.data.source.local.room.NewsDatabase
import com.example.nytimes_news_feed.core.data.source.local.room.RemoteKeysDao

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): NewsDatabase = Room.databaseBuilder(
        context,
        NewsDatabase::class.java, "NYtimeApi.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideTourismDao(database: NewsDatabase): NewsDao = database.provideNewsDao()

    @Provides
    fun provideRemoteKeysDao(database: NewsDatabase): RemoteKeysDao = database.provideRemoteKeys()

    @Provides
    fun provideFavoriteDao(database: NewsDatabase): FavoriteDao = database.provideFavoriteDao()
}