package com.thaonx.mockt3h.view.fragment.covid


import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.databinding.FragmentCovidVietnamBinding
import com.thaonx.mockt3h.viewmodel.ItemViewModel


@AndroidEntryPoint
class CovidVietnamFragment : BaseFragment<FragmentCovidVietnamBinding>(
    FragmentCovidVietnamBinding::inflate
) {

    private val itemViewModel: ItemViewModel by viewModels()

    override fun obServerLivedata() {
            itemViewModel.covidResponse.observe(viewLifecycleOwner) { covid ->
                for (element in covid.countries) {
                    if (element.countryName == "Vietnam") {
                        binding.totalCases.text = element.cases
                        binding.totalDeaths.text = element.deaths
                        binding.totalRecovered.text = element.totalRecovered
                        binding.totalNewCases.text = element.newCases
                        break
                    }
                }
            }
    }

    override fun initData() {
        itemViewModel.getAllCovid()
    }
}