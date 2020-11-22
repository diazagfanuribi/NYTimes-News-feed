package com.example.nytimes_news_feed.core.domain.usecase.detail

import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import kotlinx.coroutines.flow.Flow

interface DetailUseCase {
    suspend fun addFavorite(newsApi: NewsApi)
    suspend fun deleteFavorite(newsApi: NewsApi)
    fun getAllFavoriteId(): Flow<List<String>>
}