package com.example.nytimes_news_feed.core.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bumptech.glide.load.engine.Resource
import com.example.nytimes_news_feed.core.data.source.local.LocalDataSource
import com.example.nytimes_news_feed.core.data.source.local.entity.NewsEntity
import com.example.nytimes_news_feed.core.data.source.remote.RemoteDataSource
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.core.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): IHomeRepository {

    override fun getSearchResultStream(query: String): Flow<PagingData<NewsEntity>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { localDataSource.getReposByName(dbQuery) }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 20),
            remoteMediator = NewsRemoteMediator(
                remoteDataSource,
                localDataSource,
                query
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

}