package com.example.nytimes_news_feed.core.domain.repository

import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface IDetailRepository {
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)
    fun getAllFavoriteId(): Flow<List<String>>
}