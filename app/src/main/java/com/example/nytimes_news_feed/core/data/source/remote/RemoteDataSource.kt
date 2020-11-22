package com.example.nytimes_news_feed.core.data.source.remote

import com.example.nytimes_news_feed.core.data.source.remote.network.ApiService
import com.example.nytimes_news_feed.core.data.source.remote.response.NewsApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun searchNewsFeed(query : String, page : Int): NewsApiResponse {
       return apiService.searchNewsFeed(query = query,page = page)
    }
}