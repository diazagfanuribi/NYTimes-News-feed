package com.example.nytimes_news_feed.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.core.domain.usecase.detail.DetailUseCase
import com.example.nytimes_news_feed.core.domain.usecase.favorite.FavoriteUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(val repository : FavoriteUseCase): ViewModel(){
    fun getFavorite(): Flow<PagingData<NewsApi>> {
        return repository.getFavorites()
            .cachedIn(viewModelScope)
    }
}