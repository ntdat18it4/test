package com.thaonx.mockt3h.view.fragment.news


import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.databinding.FragmentDetailBinding
import com.thaonx.mockt3h.model.Articles
import com.thaonx.mockt3h.model.ArticlesFavorite
import com.thaonx.mockt3h.utils.Constants
import com.thaonx.mockt3h.viewmodel.ItemViewModel


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(
    FragmentDetailBinding::inflate
) {

    private val args: DetailFragmentArgs by navArgs()

    private val itemViewModel: ItemViewModel by activityViewModels()

    private lateinit var currentArticles: Articles

    override fun initView() {

        currentArticles = args.articles!!

        setupWebView()

        binding.btnAdd.setOnClickListener {
            addFavoriteArticle(requireView())
        }

        binding.btnShare.setOnClickListener {
            share()
        }
    }

    private fun share() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT,
            currentArticles.url) // Simple text and URL to share

        sendIntent.type = "text/plain"
        this.startActivity(sendIntent)
    }

    private fun addFavoriteArticle(view: View) {
        val favArticles = ArticlesFavorite(
            name = currentArticles.source.name,
            title = currentArticles.title,
            url = currentArticles.url,
            urlToImage = currentArticles.urlToImage,
            publishedAt = currentArticles.publishedAt
        )

        itemViewModel.insertArticleFavorite(favArticles)
        Snackbar.make(view, Constants.NOTIFY, Snackbar.LENGTH_SHORT).show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(currentArticles.url)
        }
    }
}