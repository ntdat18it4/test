package com.thaonx.mockt3h.view.fragment.news


import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.adapter.PagerMainNewsAdapter
import com.thaonx.mockt3h.adapter.PagerMainNewsAdapter.Companion.PAGE_LATEST_NEWS
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.databinding.FragmentMainNewsBinding
import com.thaonx.mockt3h.utils.Constants


@AndroidEntryPoint
class MainNewsFragment : BaseFragment<FragmentMainNewsBinding>(
    FragmentMainNewsBinding::inflate
) {
    private val tabs = arrayListOf(
        Constants.LATEST_NEWS,
        Constants.VIETNAM,
        Constants.WORLD,
        Constants.ENTERTAINMENT,
        Constants.SPORT)

    override fun initView() {
        setupTabLayout()
    }

    private fun setupTabLayout() {
        val pagerMainNewsAdapter = PagerMainNewsAdapter(requireActivity())
        binding.vpMainNews.adapter = pagerMainNewsAdapter
        binding.vpMainNews.currentItem = PAGE_LATEST_NEWS
       binding.vpMainNews.isUserInputEnabled = false
        TabLayoutMediator(binding.tabMainNews, binding.vpMainNews) { tab, index ->
            tab.text = tabs[index]
        }.attach()
    }

}