package com.thaonx.mockt3h.view.fragment.news


import android.graphics.Color
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.adapter.ItemMainNewsAdapter
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.databinding.FragmentLatestNewsBinding
import com.thaonx.mockt3h.utils.Constants
import com.thaonx.mockt3h.viewmodel.ItemViewModel

@AndroidEntryPoint
class LatestNewsFragment : BaseFragment<FragmentLatestNewsBinding>(
    FragmentLatestNewsBinding::inflate
) {
    private val itemLatestAdapter = ItemMainNewsAdapter()
    private val itemViewModel: ItemViewModel by viewModels()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun obServerLivedata() {
        itemViewModel.itemNewsResponse.observe(viewLifecycleOwner) { itemLatest ->
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.rvLatestNews.visibility = View.VISIBLE
            itemLatestAdapter.differ.submitList(itemLatest.articles)

            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun initData() {
        itemViewModel.getAllNews(Constants.Q_LATEST)
    }

    override fun initView() {
        setupRecyclerviewLatest()
        setupSwipeRefreshLayout()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout = binding.swipeRefreshLayout

        //swipe update data
        swipeRefreshLayout.setOnRefreshListener {
            obServerLivedata()
        }

        swipeRefreshLayout.setColorSchemeColors(
            Color.BLUE
        )
    }

    private fun setupRecyclerviewLatest() {
        binding.rvLatestNews.apply {
            adapter = itemLatestAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }
}