package com.example.nytimes_news_feed.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import androidx.paging.LoadState
import com.example.nytimes_news_feed.MyApplication
import com.example.nytimes_news_feed.R
import com.example.nytimes_news_feed.core.domain.model.NewsApi
import com.example.nytimes_news_feed.core.ui.ViewModelFactory
import com.example.nytimes_news_feed.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
class HomeFragment : Fragment(R.layout.fragment_home), NewsAdapter.OnItemClickListener {

    @Inject
    lateinit var factory: ViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels {
        factory
    }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var searchJob: Job? = null

    private lateinit var adapter: NewsAdapter

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {

            homeViewModel.searchRepo(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        adapter = NewsAdapter(this)

        binding.apply {
            rvNews.setHasFixedSize(true)
            rvNews.adapter = adapter
            rvNews.itemAnimator = null
            rvNews.adapter = adapter.withLoadStateFooter(
                footer = NewsLoadStateAdapter { adapter.retry() }
            )

            adapter.addLoadStateListener { loadState ->
                // Only show the list if refresh succeeds.
                binding.rvNews.isVisible = loadState.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

                retryButton.setOnClickListener {
                    adapter.retry()
                }
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1) {
                    rvNews.isVisible = false
                    retryButton.isVisible = true
                }
            }
        }
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        initSearch(query)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)  {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_favorite -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initAdapter() {
        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.rvNews.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            if (loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached &&
                adapter.itemCount < 1) {
                binding.rvNews.isVisible = false
            }
        }
    }

    private fun initSearch(query: String) {
        binding.searchNews.setText(query)

        binding.searchNews.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchNews.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

    }

    private fun updateRepoListFromInput() {
        binding.searchNews.text?.trim()?.let {
            if (it.isNotEmpty()) {
                binding.rvNews.scrollToPosition(0)
                search(it.toString())
            }
        }
        binding.searchNews.clearFocus()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onItemClick(news: NewsApi) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(news)
        findNavController().navigate(action)
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "indonesian"
    }
}