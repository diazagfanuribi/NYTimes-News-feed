package com.example.nytimes_news_feed.core.data.source.remote.response

import android.os.Parcelable
import android.util.Log
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class NewsApiResponse(
    val copyright: String,
    val response: Response,
    val status: String
)


data class Response(
    val docs: List<Doc>
)


data class Doc(
    val _id: String,
    val byline: Byline?,
    val document_type: String?,
    val headline: Headline?,
    val lead_paragraph: String,
    val multimedia: List<Multimedia>?= emptyList(),
    val pub_date: String?,
    val snippet: String?,
    val source: String?,
    val web_url: String?
){
    var imageUrl : String? = {
        if (multimedia.isNullOrEmpty()){
            "A"
        }
        else{
             multimedia.filter {
                !it.url.isNullOrEmpty()
            }.get(1).url
            Log.i("News Image", multimedia.toString())
        }
    }.toString()
}

data class Byline(
    val original: String?
)

data class Headline(
    val main: String?
)

data class Multimedia(
    @SerializedName("url")
    val url: String? = "images/2020/10/28/dining/27Lee1/23Lee1-articleLarge.jpg"
)



