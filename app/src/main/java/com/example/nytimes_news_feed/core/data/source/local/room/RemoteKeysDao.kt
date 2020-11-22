package com.example.nytimes_news_feed.core.data.source.local.room

import androidx.room.*
import com.example.nytimes_news_feed.core.data.source.local.entity.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE newsId = :newsId")
    suspend fun remoteKeysNewsId(newsId: String): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()


    @Transaction
    suspend fun deleteWithTransaction() {
        clearRemoteKeys()
    }

    @Transaction
    suspend fun insertAllWithTransaction(remoteKeys: List<RemoteKeys>) {
        insertAll(remoteKeys)
    }
}