package com.thaonx.mockt3h.view.fragment.covid


import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.databinding.FragmentCovidWorldBinding
import com.thaonx.mockt3h.viewmodel.ItemViewModel


@AndroidEntryPoint
class CovidWorldFragment : BaseFragment<FragmentCovidWorldBinding>(
    FragmentCovidWorldBinding::inflate
) {

    private val itemViewModel: ItemViewModel by viewModels()

    override fun obServerLivedata() {
        itemViewModel.covidResponse.observe(viewLifecycleOwner) { covid ->
            binding.totalCases.text = covid.worldTotal.cases
            binding.totalDeaths.text = covid.worldTotal.deaths
            binding.totalRecovered.text = covid.worldTotal.totalRecovered
            binding.totalNewCases.text = covid.worldTotal.newCases
        }
    }

    override fun initData() {
        itemViewModel.getAllCovid()
    }

    override fun initView() {

    }
}