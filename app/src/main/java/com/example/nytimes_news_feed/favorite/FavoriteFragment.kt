package com.example.nytimes_news_feed.favorite

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.nytimes_news_feed.MyApplication
import com.example.nytimes_news_feed.R
import com.example.nytimes_news_feed.core.ui.ViewModelFactory
import com.example.nytimes_news_feed.databinding.FragmentFavoriteBinding
import com.example.nytimes_news_feed.databinding.FragmentHomeBinding
import com.example.nytimes_news_feed.home.HomeViewModel
import javax.inject.Inject

class FavoriteFragment: Fragment(R.layout.fragment_favorite) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }
}