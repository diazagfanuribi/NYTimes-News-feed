package com.example.nytimes_news_feed.di

import com.example.nytimes_news_feed.core.domain.usecase.detail.DetailInteractor
import com.example.nytimes_news_feed.core.domain.usecase.detail.DetailUseCase
import com.example.nytimes_news_feed.core.domain.usecase.favorite.FavoriteInteractor
import com.example.nytimes_news_feed.core.domain.usecase.favorite.FavoriteUseCase
import com.example.nytimes_news_feed.core.domain.usecase.home.HomeInteractor
import com.example.nytimes_news_feed.core.domain.usecase.home.HomeUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideHomeUseCase(homeInteractor: HomeInteractor): HomeUseCase

    @Binds
    abstract fun provideDetailUseCase(detailInteractor: DetailInteractor): DetailUseCase

    @Binds
    abstract fun provideFavoriteUseCase(favoriteInteractor: FavoriteInteractor): FavoriteUseCase

}
