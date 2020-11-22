package com.example.nytimes_news_feed.core.domain.usecase.favorite

import androidx.paging.PagingData
import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase{
    fun getFavorites(): Flow<PagingData<NewsApi>>
}