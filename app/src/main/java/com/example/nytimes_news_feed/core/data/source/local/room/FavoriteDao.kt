package com.example.nytimes_news_feed.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("SELECT * FROM favorite" + " LIMIT :pageSize " + "OFFSET :page")
    suspend fun getFavorites(pageSize : Int, page : Int): List<FavoriteEntity>

    @Query("SELECT COUNT(idFavorite) FROM favorite")
    suspend fun countFavorite(): Int

    @Query("SELECT idFavorite FROM favorite")
    fun getAllFavoriteId(): Flow<List<String>>
}