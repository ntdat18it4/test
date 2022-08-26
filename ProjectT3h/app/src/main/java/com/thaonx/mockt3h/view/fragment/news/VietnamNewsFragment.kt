package com.thaonx.mockt3h.view.fragment.news


import android.graphics.Color
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.adapter.ItemMainNewsAdapter
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.databinding.FragmentVietNamNewsBinding
import com.thaonx.mockt3h.utils.Constants
import com.thaonx.mockt3h.viewmodel.ItemViewModel

@AndroidEntryPoint
class VietnamNewsFragment : BaseFragment<FragmentVietNamNewsBinding>(
    FragmentVietNamNewsBinding::inflate
) {
    private var itemVietnamNewsAdapter = ItemMainNewsAdapter()
    private val itemViewModel: ItemViewModel by viewModels()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun obServerLivedata() {

        itemViewModel.itemNewsResponse.observe(viewLifecycleOwner) { itemVietnam ->
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.rvVietnamNews.visibility = View.VISIBLE
            itemVietnamNewsAdapter.differ.submitList(itemVietnam.articles)

            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun initData() {
        itemViewModel.getAllNews(Constants.Q_VIETNAM)
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
        binding.rvVietnamNews.apply {
            adapter = itemVietnamNewsAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }
}