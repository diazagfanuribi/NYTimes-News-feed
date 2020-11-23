package com.example.nytimes_news_feed.favorite

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.nytimes_news_feed.MyApplication
import com.example.nytimes_news_feed.R
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.core.ui.ViewModelFactory
import com.example.nytimes_news_feed.databinding.FragmentFavoriteBinding
import com.example.nytimes_news_feed.home.NewsLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment: Fragment(R.layout.fragment_favorite), FavoriteAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteBinding.bind(view)

        adapter = FavoriteAdapter(this)

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.itemAnimator = null
            rvFavorite.adapter = adapter.withLoadStateFooter(
                footer = NewsLoadStateAdapter { adapter.retry() }
            )
            retryButton.setOnClickListener {
                adapter.retry()
            }
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvFavorite.isVisible = loadState.source.refresh is LoadState.NotLoading
                retryButton.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1) {
                    rvFavorite.isVisible = false
                }
            }
        }

        lifecycleScope.launch {
            favoriteViewModel.getFavorite().collectLatest {
                adapter.submitData(it)
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onItemClick(news: NewsApi) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(news)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}