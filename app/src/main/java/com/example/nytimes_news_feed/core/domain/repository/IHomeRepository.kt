package com.example.nytimes_news_feed.core.domain.repository

import androidx.paging.PagingData
import com.example.nytimes_news_feed.core.data.source.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface IHomeRepository {
    fun getSearchResultStream(query: String): Flow<PagingData<NewsEntity>>
}