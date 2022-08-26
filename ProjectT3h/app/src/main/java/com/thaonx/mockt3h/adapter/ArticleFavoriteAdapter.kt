package com.thaonx.mockt3h.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.thaonx.mockt3h.click.ClickDeleteItem
import com.thaonx.mockt3h.databinding.ItemFavoriteBinding
import com.thaonx.mockt3h.model.Articles
import com.thaonx.mockt3h.model.ArticlesFavorite
import com.thaonx.mockt3h.model.Sources
import com.thaonx.mockt3h.utils.Constants
import com.thaonx.mockt3h.view.fragment.news.FavoriteFragmentDirections

class ArticleFavoriteAdapter(private var clickDeleteItem: ClickDeleteItem) :
    RecyclerView.Adapter<ArticleFavoriteAdapter.ArticleFavoriteViewHolder>() {

    private var binding: ItemFavoriteBinding? = null

    private var viewBinderHelper = ViewBinderHelper()

    private val diffUtil = object : DiffUtil.ItemCallback<ArticlesFavorite>() {
        override fun areItemsTheSame(
            oldItem: ArticlesFavorite,
            newItem: ArticlesFavorite,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ArticlesFavorite,
            newItem: ArticlesFavorite,
        ): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleFavoriteViewHolder {
        binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleFavoriteViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ArticleFavoriteViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ArticleFavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articlesFavorite: ArticlesFavorite) {
            binding.articleFavorite = articlesFavorite

            Glide.with(itemView.context).load(articlesFavorite.urlToImage).into(binding.imgFavorite)
            binding.tvTimeFavorite.text = Constants.dateToTimeFormat(articlesFavorite.publishedAt)

            binding.layoutNews.setOnClickListener { mView ->

                val sources = Sources(articlesFavorite.name)

                val article = Articles(
                    title = articlesFavorite.title,
                    urlToImage = articlesFavorite.urlToImage,
                    source = sources,
                    publishedAt = articlesFavorite.publishedAt,
                    url = articlesFavorite.url,
                    description = ""
                )

                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(article)
                mView.findNavController().navigate(action)
            }

            viewBinderHelper.bind(binding.swipeRevealLayout,articlesFavorite.id.toString())

            binding.layoutDelete.setOnClickListener {
                clickDeleteItem.deleteItem(articlesFavorite)
            }
        }
    }

}