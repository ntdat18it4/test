package com.thaonx.mockt3h.view.fragment.news


import android.graphics.Color
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.adapter.ItemMainNewsAdapter
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.databinding.FragmentWorldNewsBinding
import com.thaonx.mockt3h.utils.Constants
import com.thaonx.mockt3h.viewmodel.ItemViewModel


@AndroidEntryPoint
class WorldNewsFragment : BaseFragment<FragmentWorldNewsBinding>(
    FragmentWorldNewsBinding::inflate
) {
    private var itemWorldNewsAdapter = ItemMainNewsAdapter()
    private val itemViewModel: ItemViewModel by viewModels()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun obServerLivedata() {

        itemViewModel.itemNewsResponse.observe(viewLifecycleOwner) { itemWorld ->
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.rvWordNews.visibility = View.VISIBLE
            itemWorldNewsAdapter.differ.submitList(itemWorld.articles)

            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun initData() {
        itemViewModel.getAllNews(Constants.Q_WORLD)
    }

    override fun initView() {

        setupRecyclerview()
        setupRefreshLayout()

    }

    private fun setupRefreshLayout() {
        swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {
            obServerLivedata()
        }

        swipeRefreshLayout.setColorSchemeColors(
            Color.BLUE
        )
    }

    private fun setupRecyclerview() {
        binding.rvWordNews.apply {
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = itemWorldNewsAdapter
        }
    }

}