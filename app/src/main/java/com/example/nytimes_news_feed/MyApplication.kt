package com.example.nytimes_news_feed

import android.app.Application
import com.example.nytimes_news_feed.core.di.CoreComponent
import com.example.nytimes_news_feed.core.di.DaggerCoreComponent
import com.example.nytimes_news_feed.di.AppComponent
import com.example.nytimes_news_feed.di.DaggerAppComponent

class MyApplication : Application(){
    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}