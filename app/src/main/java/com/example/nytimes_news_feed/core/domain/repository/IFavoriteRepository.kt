package com.example.nytimes_news_feed.core.domain.repository

import androidx.paging.PagingData
import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository{
    fun getFavorites(): Flow<PagingData<FavoriteEntity>>
}