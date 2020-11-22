package com.example.nytimes_news_feed.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsApi(
        val id : String,
        val title:String?,
        val snippet : String?,
        val imageUrl: String?,
        val leadParagraph : String?,
        val website : String?,
        val byline : String?,
        val pubDate : String?,
        val source : String?
): Parcelable{

}