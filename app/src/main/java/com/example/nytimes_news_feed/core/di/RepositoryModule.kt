package com.example.nytimes_news_feed.core.di
import dagger.Binds
import dagger.Module


@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

//    @Binds
//    abstract fun provideRepository(tourismRepository: TourismRepository): ITourismRepository

}