package com.example.nytimes_news_feed.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.core.domain.usecase.home.HomeUseCase
import com.example.nytimes_news_feed.core.utils.Mapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeViewModel @Inject constructor(val repository : HomeUseCase) : ViewModel() {
    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<NewsApi>>? = null

    fun searchRepo(queryString: String): Flow<PagingData<NewsApi>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<NewsApi>> = repository.getSearchResultStream(queryString)
            .map { pagingData->
                pagingData.map {
                    Mapper.mapNewsEntitytoDomain(it)
                }
            }
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}