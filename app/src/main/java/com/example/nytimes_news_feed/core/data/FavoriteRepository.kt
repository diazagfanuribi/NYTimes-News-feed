package com.example.nytimes_news_feed.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.nytimes_news_feed.core.data.source.local.LocalDataSource
import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity
import com.example.nytimes_news_feed.core.domain.repository.IFavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val localDataSource: LocalDataSource): IFavoriteRepository{
    override fun getFavorites() : Flow<PagingData<FavoriteEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40),
            pagingSourceFactory = { FavoritePagingSource(localDataSource = localDataSource) }
        ).flow
    }
}