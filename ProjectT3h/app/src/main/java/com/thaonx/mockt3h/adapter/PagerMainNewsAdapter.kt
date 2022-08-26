package com.thaonx.mockt3h.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thaonx.mockt3h.view.fragment.news.*

class PagerMainNewsAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    companion object {
        const val TOTAL_PAGE_NEWS = 5
        const val PAGE_LATEST_NEWS = 0
        const val PAGE_VIETNAM_NEWS = 1
        const val PAGE_WORLD_NEWS = 2
        const val PAGE_ENTERTAINMENT_NEWS = 3
        const val PAGE_SPORT_NEWS = 4
    }

    override fun getItemCount(): Int {
        return TOTAL_PAGE_NEWS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_LATEST_NEWS -> LatestNewsFragment()
            PAGE_VIETNAM_NEWS -> VietnamNewsFragment()
            PAGE_WORLD_NEWS -> WorldNewsFragment()
            PAGE_ENTERTAINMENT_NEWS -> EntertainmentFragment()
            PAGE_SPORT_NEWS -> SportFragment()
            else -> LatestNewsFragment()
        }
    }
}