package com.example.nytimes_news_feed.core.domain.usecase.detail

import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.core.domain.repository.IDetailRepository
import com.example.nytimes_news_feed.core.utils.Mapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailInteractor @Inject constructor(private val repository : IDetailRepository) : DetailUseCase{
    override suspend fun addFavorite(newsApi: NewsApi) {
        val entity = Mapper.mapFavoriteDomaintoEntity(newsApi)
        repository.addFavorite(entity)
    }

    override suspend fun deleteFavorite(newsApi: NewsApi) {
        val entity = Mapper.mapFavoriteDomaintoEntity(newsApi)
        repository.deleteFavorite(entity)
    }

    override fun getAllFavoriteId(): Flow<List<String>> {
        return repository.getAllFavoriteId()
    }
}