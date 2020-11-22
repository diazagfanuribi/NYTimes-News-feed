package com.example.nytimes_news_feed.core.domain.usecase.home

import androidx.paging.PagingData
import com.example.nytimes_news_feed.core.data.source.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun getSearchResultStream(query: String): Flow<PagingData<NewsEntity>>
}