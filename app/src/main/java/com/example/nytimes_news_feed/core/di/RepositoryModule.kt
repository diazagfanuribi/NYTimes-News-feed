package com.example.nytimes_news_feed.core.di
import com.example.nytimes_news_feed.core.data.DetailRepository
import com.example.nytimes_news_feed.core.data.FavoriteRepository
import com.example.nytimes_news_feed.core.data.HomeRepository
import com.example.nytimes_news_feed.core.domain.repository.IDetailRepository
import com.example.nytimes_news_feed.core.domain.repository.IFavoriteRepository
import com.example.nytimes_news_feed.core.domain.repository.IHomeRepository
import dagger.Binds
import dagger.Module


@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideHomeRepository(homeRepository: HomeRepository): IHomeRepository

    @Binds
    abstract fun provideDetailRepository(detailRepository: DetailRepository): IDetailRepository

    @Binds
    abstract fun provideFavoritelRepository(favoriteRepository: FavoriteRepository): IFavoriteRepository
}