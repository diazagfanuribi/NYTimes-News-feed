package com.example.nytimes_news_feed.core.data

import androidx.paging.PagingSource
import com.example.nytimes_news_feed.core.data.source.local.LocalDataSource
import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity

private const val STARTING_PAGE = 0
class FavoritePagingSource(private val localDataSource: LocalDataSource) : PagingSource<Int, FavoriteEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteEntity> {
        val position = params.key ?: STARTING_PAGE

        return try {
            val data = localDataSource.getFavorites(
                pageSize = params.loadSize,
                page = position)

            val total = localDataSource.getCountFavorites()/params.loadSize

            LoadResult.Page(
                data = data,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = if (position == total) null else position + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}