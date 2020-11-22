package com.example.nytimes_news_feed.core.di

import android.content.Context
import com.example.nytimes_news_feed.core.domain.repository.IDetailRepository
import com.example.nytimes_news_feed.core.domain.repository.IFavoriteRepository
import com.example.nytimes_news_feed.core.domain.repository.IHomeRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideHomeRepository() : IHomeRepository
    fun provideDetailRepository() : IDetailRepository
    fun provideFavoriteRepository() : IFavoriteRepository
}