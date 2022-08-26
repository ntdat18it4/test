package com.thaonx.mockt3h.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thaonx.mockt3h.view.fragment.covid.CovidVietnamFragment
import com.thaonx.mockt3h.view.fragment.covid.CovidWorldFragment

class PagerCovidAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    companion object{
        const val TOTAL_PAGE = 2
        const val PAGE_VIETNAM = 0
        const val PAGE_WORLD = 1
    }

    override fun getItemCount(): Int {
        return TOTAL_PAGE
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_VIETNAM -> CovidVietnamFragment()
            PAGE_WORLD -> CovidWorldFragment()
            else -> CovidVietnamFragment()
        }
    }
}