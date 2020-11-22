package com.example.nytimes_news_feed.core.data.source.remote.network

import com.example.nytimes_news_feed.BuildConfig
import com.example.nytimes_news_feed.core.data.source.remote.response.NewsApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val API_KEY = "9b693ffaa5fe451090146e5c90fbed78"
    }

    @GET("articlesearch.json")
    suspend fun searchNewsFeed(@Query("api-key") apiKey: String = API_KEY,
                    @Query("page") page: Int,
                    @Query("q") query: String) : NewsApiResponse
}