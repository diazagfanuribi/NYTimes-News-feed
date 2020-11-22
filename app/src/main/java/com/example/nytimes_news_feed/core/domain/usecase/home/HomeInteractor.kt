package com.example.nytimes_news_feed.core.domain.usecase.home

import androidx.paging.PagingData
import com.example.nytimes_news_feed.core.data.source.local.entity.NewsEntity
import com.example.nytimes_news_feed.core.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeInteractor @Inject
constructor(private val homeRepository: IHomeRepository) : HomeUseCase {
    override fun getSearchResultStream(query: String): Flow<PagingData<NewsEntity>> {
            return homeRepository.getSearchResultStream(query)
    }
}