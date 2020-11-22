package com.example.nytimes_news_feed.core.utils

import com.example.nytimes_news_feed.core.data.source.local.entity.FavoriteEntity
import com.example.nytimes_news_feed.core.data.source.local.entity.NewsEntity
import com.example.nytimes_news_feed.core.data.source.remote.response.Doc
import com.example.nytimes_news_feed.core.domain.model.NewsApi

object Mapper {
    fun mapNewsResponsetoDomain(docs: List<Doc>): List<NewsApi> {
        val newsApis = ArrayList<NewsApi>()
        docs.map {
            val newsApi = NewsApi(
                id = it._id,
                title = it.headline?.main,
                snippet = it.snippet,
                imageUrl = "https://www.nytimes.com/${it.imageUrl ?: "images/2020/10/28/dining/27Lee1/23Lee1-articleLarge.jpg"}",
                leadParagraph = it.lead_paragraph,
                website = it.web_url,
                byline = it.byline?.original,
                pubDate = it.pub_date,
                source = it.source
            )
            newsApis.add(newsApi)
        }
        return newsApis
    }

    fun mapNewsResponsetoEntities(docs: List<Doc>): List<NewsEntity> {
        val newsEntities = ArrayList<NewsEntity>()
        docs.map {
            val newsEntity = NewsEntity(
                id = it._id,
                title = it.headline?.main,
                snippet = it.snippet,
                imageUrl = it.imageUrl ?: "NA",
                leadParagraph = it.lead_paragraph,
                website = it.web_url,
                byline = it.byline?.original,
                pubDate = it.pub_date,
                source = it.source
            )
            newsEntities.add(newsEntity)
        }
        return newsEntities
    }

    fun mapNewsEntitiestoDomain(newsEntities: List<NewsEntity>): List<NewsApi> {
        val newsApis = ArrayList<NewsApi>()
        newsEntities.map {
            val newsApi = NewsApi(
                id = it.id,
                title = it.title,
                snippet = it.snippet,
                imageUrl = it.imageUrl,
                leadParagraph = it.leadParagraph,
                website = it.website,
                byline = it.byline,
                pubDate = it.pubDate,
                source = it.source
            )
            newsApis.add(newsApi)
        }
        return newsApis
    }

    fun mapNewsEntitytoDomain(it: NewsEntity): NewsApi {
        val newsApi = NewsApi(
            id = it.id,
            title = it.title,
            snippet = it.snippet,
            imageUrl = it.imageUrl,
            leadParagraph = it.leadParagraph,
            website = it.website,
            byline = it.byline,
            pubDate = it.pubDate,
            source = it.source
        )
        return newsApi
    }

    fun mapNewsDomainstoEntities(input: List<NewsApi>): List<NewsEntity> {
        val newsApis = ArrayList<NewsEntity>()
        input.map {
            val newsApi = NewsEntity(
                id = it.id,
                title = it.title,
                snippet = it.snippet,
                imageUrl = it.imageUrl,
                leadParagraph = it.leadParagraph,
                website = it.website,
                byline = it.byline,
                pubDate = it.pubDate,
                source = it.source
            )
            newsApis.add(newsApi)
        }
        return newsApis
    }

    fun mapFavoriteEntitytoDomain(it: FavoriteEntity): NewsApi {
        val newsApi = NewsApi(
            id = it.id,
            title = it.title,
            snippet = it.snippet,
            imageUrl = it.imageUrl,
            leadParagraph = it.leadParagraph,
            website = it.website,
            byline = it.byline,
            pubDate = it.pubDate,
            source = it.source
        )
        return newsApi
    }

    fun mapFavoriteDomainstoEntities(input: List<NewsApi>): List<FavoriteEntity> {
        val newsApis = ArrayList<FavoriteEntity>()
        input.map {
            val newsApi = FavoriteEntity(
                id = it.id,
                title = it.title,
                snippet = it.snippet,
                imageUrl = it.imageUrl,
                leadParagraph = it.leadParagraph,
                website = it.website,
                byline = it.byline,
                pubDate = it.pubDate,
                source = it.source
            )
            newsApis.add(newsApi)
        }
        return newsApis
    }

    fun mapFavoriteDomaintoEntity(it: NewsApi): FavoriteEntity {
        val newsApi = FavoriteEntity(
            id = it.id,
            title = it.title,
            snippet = it.snippet,
            imageUrl = it.imageUrl,
            leadParagraph = it.leadParagraph,
            website = it.website,
            byline = it.byline,
            pubDate = it.pubDate,
            source = it.source
        )
        return newsApi
    }
}