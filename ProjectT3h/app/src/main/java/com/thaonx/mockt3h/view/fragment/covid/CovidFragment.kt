package com.thaonx.mockt3h.view.fragment.covid


import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.R
import com.thaonx.mockt3h.adapter.PagerCovidAdapter
import com.thaonx.mockt3h.adapter.PagerCovidAdapter.Companion.PAGE_VIETNAM
import com.thaonx.mockt3h.adapter.PagerCovidAdapter.Companion.PAGE_WORLD
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.databinding.FragmentCovidBinding
import com.thaonx.mockt3h.utils.DepthPageTransformer


@AndroidEntryPoint
class CovidFragment : BaseFragment<FragmentCovidBinding>(
    FragmentCovidBinding::inflate
) {

    private lateinit var pagerCovidAdapter: PagerCovidAdapter

    override fun initView() {
        setupViewpagerCovid()
        setupBottomNavigationCovid()
    }

    private fun setupViewpagerCovid() {
        binding.bottomPageCovid.itemIconTintList = null
        pagerCovidAdapter = PagerCovidAdapter(requireActivity())
        binding.vpCovid.adapter = pagerCovidAdapter
        binding.vpCovid.setPageTransformer(DepthPageTransformer())
        binding.vpCovid.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    PAGE_VIETNAM -> binding.bottomPageCovid.menu.findItem(R.id.bottomCovidVietnam).isChecked =
                        true
                    PAGE_WORLD -> binding.bottomPageCovid.menu.findItem(R.id.bottomCovidWorld).isChecked =
                        true
                }
            }
        })
    }

    private fun setupBottomNavigationCovid() {
        binding.bottomPageCovid.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottomCovidVietnam -> binding.vpCovid.currentItem = PAGE_VIETNAM
                R.id.bottomCovidWorld -> binding.vpCovid.currentItem = PAGE_WORLD
            }
            return@setOnItemSelectedListener true
        }
    }

}