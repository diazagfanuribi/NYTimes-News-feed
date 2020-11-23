package com.example.nytimes_news_feed.core.data

import com.example.nytimes_news_feed.core.data.source.local.LocalDataSource
import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity
import com.example.nytimes_news_feed.core.domain.repository.IDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepository @Inject
constructor(private val localDataSource: LocalDataSource): IDetailRepository {
    override suspend fun addFavorite(favoriteEntity: FavoriteEntity) {
        localDataSource.addFavorite(favoriteEntity)
    }

    override suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        localDataSource.deleteFavorite(favoriteEntity)
    }

    override  fun getAllFavoriteId(): Flow<List<String>> {
        return localDataSource.getAllFavoriteId()
    }
}