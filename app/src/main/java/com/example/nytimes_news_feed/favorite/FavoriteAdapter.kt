package com.example.nytimes_news_feed.favorite

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.nytimes_news_feed.R
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.databinding.RepoViewItemBinding


class FavoriteAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<NewsApi, FavoriteAdapter.FavoriteViewHolder>(
        COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = RepoViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        getItem(position)?.let {
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
    inner class FavoriteViewHolder(private val binding: RepoViewItemBinding
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
            binding.textviewSnippet.text = news.snippet
            binding.textviewDate.text = news.pubDate
            Glide.with(itemView)
                .load(news.imageUrl)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(binding.poster)

        }

    }


}