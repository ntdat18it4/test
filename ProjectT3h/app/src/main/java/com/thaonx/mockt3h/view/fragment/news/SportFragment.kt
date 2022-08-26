package com.thaonx.mockt3h.view.fragment.news


import android.graphics.Color
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.adapter.ItemMainNewsAdapter
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.databinding.FragmentSportBinding
import com.thaonx.mockt3h.utils.Constants
import com.thaonx.mockt3h.viewmodel.ItemViewModel


@AndroidEntryPoint
class SportFragment : BaseFragment<FragmentSportBinding>(
    FragmentSportBinding::inflate
) {

    private val itemViewModel: ItemViewModel by viewModels()
    private val itemSportNewsAdapter = ItemMainNewsAdapter()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun obServerLivedata() {
        itemViewModel.itemNewsResponse.observe(viewLifecycleOwner) { itemNews ->
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.rvSportNews.visibility = View.VISIBLE
            itemSportNewsAdapter.differ.submitList(itemNews.articles)

            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun initData() {
        itemViewModel.getAllNews(Constants.Q_SPORT)
    }

    override fun initView() {
        setupRecyclerview()
        setupSwipeRefreshLayout()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {
            obServerLivedata()
        }

        swipeRefreshLayout.setColorSchemeColors(
            Color.BLUE
        )
    }

    private fun setupRecyclerview() {
        binding.rvSportNews.apply {
            adapter = itemSportNewsAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

}