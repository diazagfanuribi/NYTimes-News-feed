package com.example.nytimes_news_feed.core.data.source.local

import android.util.Log
import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity
import com.example.nytimes_news_feed.core.data.source.local.entity.RemoteKeys
import com.example.nytimes_news_feed.core.data.source.local.room.FavoriteDao
import com.example.nytimes_news_feed.core.data.source.local.room.IN_QUALIFIER
import com.example.nytimes_news_feed.core.data.source.local.room.NewsDao
import com.example.nytimes_news_feed.core.data.source.local.room.RemoteKeysDao
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.core.utils.Mapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource
@Inject constructor(
    private val newsDao: NewsDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val favoriteDao: FavoriteDao
) {

    suspend fun remoteKeysNewsId(id: String) = remoteKeysDao.remoteKeysNewsId(id)

    suspend fun deleteWithtransaction() {
        remoteKeysDao.deleteWithTransaction()
        newsDao.deleteWithTransaction()
    }

    suspend fun insertWithTransaction(remoteKeys: List<RemoteKeys>, newsApi: List<NewsApi>) {
        remoteKeysDao.insertAllWithTransaction(remoteKeys)
        newsDao.insertAllWithTransaction(Mapper.mapNewsDomainstoEntities(newsApi))

    }

    fun getReposByName(query: String) = newsDao.reposByName(query)

    suspend fun getFavorites(pageSize: Int, page: Int) =
        favoriteDao.getFavorites(pageSize, page)

    suspend fun getCountFavorites() = favoriteDao.countFavorite()

    suspend fun addFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.insertFavorite(favoriteEntity)
    }

    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.deleteFavorite(favoriteEntity)
    }

    fun getAllFavoriteId() = favoriteDao.getAllFavoriteId()
}