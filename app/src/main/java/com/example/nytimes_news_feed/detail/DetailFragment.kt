package com.example.nytimes_news_feed.detail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nytimes_news_feed.MyApplication
import com.example.nytimes_news_feed.R
import com.example.nytimes_news_feed.core.ui.ViewModelFactory
import com.example.nytimes_news_feed.databinding.FragmentDetailBinding
import com.example.nytimes_news_feed.databinding.FragmentHomeBinding
import com.example.nytimes_news_feed.favorite.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class DetailFragment : Fragment(R.layout.fragment_detail) {
    @Inject
    lateinit var factory: ViewModelFactory

    private val detailViewModel: DetailViewModel by viewModels {
        factory
    }
    private val args by navArgs<DetailFragmentArgs>()

    lateinit private var binding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        var isFavorite = false

        val news = args.news
        binding.apply {


            Glide.with(this@DetailFragment)
                .load(news.imageUrl)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewCreator.isVisible = true
                        textViewDescription.isVisible = news.leadParagraph != null
                        textViewTitle.isVisible  = true
                        return false
                    }
                })
                .into(imageView)

            textViewDescription.text = news.leadParagraph

            val uri = Uri.parse(news.website)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            textViewCreator.apply {
                text = news.website
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }

            textviewByline.text = news.byline

            textviewSource.text = news.source
            textViewTitle.text = news.title

            fab.setOnClickListener {
                if(isFavorite){
                    detailViewModel.deleteFavorite(news)
                }
                else {
                    detailViewModel.addFavorite(news)
                }

            }
        }


        detailViewModel.favoriteIds.observe(viewLifecycleOwner) {
            isFavorite = it.contains(news.id)
            setStatusFavorite(isFavorite)
        }


    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_star_fill_white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_star_border_white))
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }



}