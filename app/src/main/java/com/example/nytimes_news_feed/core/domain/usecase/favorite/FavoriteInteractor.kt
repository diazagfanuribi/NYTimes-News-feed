package com.example.nytimes_news_feed.core.domain.usecase.favorite

import androidx.paging.PagingData
import androidx.paging.map
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.core.domain.repository.IFavoriteRepository
import com.example.nytimes_news_feed.core.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteInteractor @Inject constructor(private val repository: IFavoriteRepository): FavoriteUseCase{
    override fun getFavorites(): Flow<PagingData<NewsApi>> {
        return repository.getFavorites()
            .map {
                pagingData -> pagingData.map { Mapper.mapFavoriteEntitytoDomain(it) }
            }
    }
}