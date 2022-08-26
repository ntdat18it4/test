package com.thaonx.mockt3h.view.fragment.news


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.adapter.ItemSearchAdapter
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.databinding.FragmentSearchBinding
import com.thaonx.mockt3h.viewmodel.ItemViewModel


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {

    private val args: SearchFragmentArgs by navArgs()

    private val itemViewModel: ItemViewModel by viewModels()
    private var itemSearchAdapter = ItemSearchAdapter()

    override fun obServerLivedata() {
        itemViewModel.itemNewsResponse.observe(requireActivity()) { item ->
            itemSearchAdapter.differ.submitList(item.articles)
        }
    }

    override fun initData() {
        val q = args.q
        q?.let {
            itemViewModel.getAllNews(it)
        }
    }

    override fun initView() {
        setupRecyclerview()
    }

    private fun setupRecyclerview() {
        binding.rvSearch.apply {
            adapter = itemSearchAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}