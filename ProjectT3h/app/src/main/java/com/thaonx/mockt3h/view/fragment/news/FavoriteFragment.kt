package com.thaonx.mockt3h.view.fragment.news


import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.adapter.ArticleFavoriteAdapter
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.click.ClickDeleteItem
import com.thaonx.mockt3h.databinding.FragmentFavoriteBinding
import com.thaonx.mockt3h.model.ArticlesFavorite
import com.thaonx.mockt3h.utils.Constants
import com.thaonx.mockt3h.viewmodel.ItemViewModel


@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    FragmentFavoriteBinding::inflate
), ClickDeleteItem {

    private val itemViewModel: ItemViewModel by viewModels()

    private var articleFavoriteAdapter = ArticleFavoriteAdapter(this)

    override fun obServerLivedata() {
        itemViewModel.articlesFavorite.observe(viewLifecycleOwner) { articleFavorites ->
            articleFavoriteAdapter.differ.submitList(articleFavorites)
            updateUi(articleFavorites)
        }
    }

    override fun initData() {
        itemViewModel.getAllArticleFavorite()
    }

    override fun initView() {
        setupRVFavorite()
    }

    private fun updateUi(articlesFavorites: List<ArticlesFavorite>) {
        if (articlesFavorites.isEmpty()) {
            binding.apply {
                rvFavoriteNews.visibility = View.GONE
                layoutNotifyFavorite.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                rvFavoriteNews.visibility = View.VISIBLE
                layoutNotifyFavorite.visibility = View.GONE
            }
        }
    }

    private fun setupRVFavorite() {
        binding.rvFavoriteNews.apply {
            adapter = articleFavoriteAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun deleteItem(articlesFavorite: ArticlesFavorite) {
        itemViewModel.deleteArticleFavorite(articlesFavorite)
        setupRVFavorite()
        Toast.makeText(requireActivity(), Constants.NOTIFY_DIALOG, Toast.LENGTH_SHORT).show()
    }
}