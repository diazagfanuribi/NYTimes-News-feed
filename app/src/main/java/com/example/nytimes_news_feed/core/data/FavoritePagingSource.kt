package com.example.nytimes_news_feed.core.data

import androidx.paging.PagingSource
import com.example.nytimes_news_feed.core.data.source.local.LocalDataSource
import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity

class FavoritePagingSource(private val localDataSource: LocalDataSource) : PagingSource<Int, FavoriteEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoriteEntity> {
        val position = params.key ?: 0

        return try {
            val data = localDataSource.getFavorites(
                pageSize = params.loadSize,
                page = position)

            LoadResult.Page(
                data = data,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (data.isNullOrEmpty()) null else position + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}