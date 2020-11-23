package com.example.nytimes_news_feed.core.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.nytimes_news_feed.core.data.source.local.LocalDataSource
import com.example.nytimes_news_feed.core.data.source.local.entity.NewsEntity
import com.example.nytimes_news_feed.core.data.source.local.entity.RemoteKeys
import com.example.nytimes_news_feed.core.data.source.local.room.IN_QUALIFIER
import com.example.nytimes_news_feed.core.data.source.local.room.NewsDatabase
import com.example.nytimes_news_feed.core.data.source.local.room.RemoteKeysDao
import com.example.nytimes_news_feed.core.data.source.remote.RemoteDataSource
import com.example.nytimes_news_feed.core.data.source.remote.network.ApiService
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.core.utils.Mapper
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

private const val STARTING_PAGE_INDEX = 0

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val query: String

    ) : RemoteMediator<Int, NewsEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state) ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val nextKey = remoteKeys.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                nextKey
            }

        }

        val apiQuery = query

        try {
            val apiResponse = remoteDataSource.searchNewsFeed(apiQuery, page)
            Log.i("NEWS", apiResponse.response.docs.toString())
            val news = Mapper.mapNewsResponsetoDomain(apiResponse.response.docs)

            val endOfPaginationReached = news.isEmpty()
            // clear all tables in the database
            if (loadType == LoadType.REFRESH) {
                localDataSource.deleteWithtransaction()
            }
            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val keys = news.map {
                RemoteKeys(newsId = it.id, prevKey = prevKey, nextKey = nextKey)
            }

            localDataSource.insertWithTransaction(keys, news)
            Log.i("NEWS", news.toString())


            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, NewsEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { news ->
                // Get the remote keys of the last item retrieved
                localDataSource.remoteKeysNewsId(news.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, NewsEntity>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull {
            it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { news ->
                // Get the remote keys of the first items retrieved
                localDataSource.remoteKeysNewsId(news.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, NewsEntity>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { newsId ->
                localDataSource.remoteKeysNewsId(newsId)
            }
        }
    }
}