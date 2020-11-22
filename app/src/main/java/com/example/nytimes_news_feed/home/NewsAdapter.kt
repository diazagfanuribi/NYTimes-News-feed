package com.example.nytimes_news_feed.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.nytimes_news_feed.R
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.databinding.ItemNewsListBinding
import com.example.nytimes_news_feed.databinding.RepoViewItemBinding

class NewsAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<NewsApi, NewsAdapter.NewsViewHolder>(
        COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = RepoViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let {
            Log.i("News data rv", it.imageUrl.toString())
            holder.bind(it)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(news: NewsApi)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<NewsApi>() {
            override fun areItemsTheSame(oldItem: NewsApi, newItem: NewsApi): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewsApi, newItem: NewsApi): Boolean {
                return oldItem == newItem
            }
        }
    }
    inner class NewsViewHolder(private val binding: RepoViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(news: NewsApi) {

            binding.title.text= news.title
            Glide.with(itemView)
                .load(news.imageUrl)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.poster)

        }


//        fun bind(news: NewsApi) {
//            with(binding) {
//                textviewNews.text = news.title
//                Glide.with(itemView)
//                    .load(news.imageUrl)
//                    .centerCrop()
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .into(imageNews)
//            }
//        }
    }


}