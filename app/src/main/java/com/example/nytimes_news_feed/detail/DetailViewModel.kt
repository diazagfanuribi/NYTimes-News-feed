package com.example.nytimes_news_feed.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.core.domain.usecase.detail.DetailUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(val detailUseCase: DetailUseCase) : ViewModel() {
    val favoriteIds = detailUseCase.getAllFavoriteId().asLiveData()
    fun addFavorite(newsApi: NewsApi) {
        viewModelScope.launch {
            detailUseCase.addFavorite(newsApi)
        }
    }

    fun deleteFavorite(newsApi: NewsApi) {
        viewModelScope.launch {
            detailUseCase.deleteFavorite(newsApi)
        }
    }
}