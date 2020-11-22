package com.example.nytimes_news_feed.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.example.nytimes_news_feed.core.data.source.local.entity.NewsEntity
import com.example.nytimes_news_feed.core.domain.model.NewsApi
const val IN_QUALIFIER = "in:title"


@Dao
interface NewsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newsEntity: List<NewsEntity>)

    @Query("SELECT * FROM news WHERE " +
            "title LIKE :queryString OR snippet LIKE :queryString OR leadParagraph LIKE :queryString")
    fun reposByName(queryString: String): PagingSource<Int, NewsEntity>

    @Query("DELETE FROM news")
    suspend fun clearRepos()

    @Transaction
    suspend fun deleteWithTransaction() {
        clearRepos()
    }

    @Transaction
    suspend fun insertAllWithTransaction(newsEntity: List<NewsEntity>) {
        insertAll(newsEntity)
    }
}