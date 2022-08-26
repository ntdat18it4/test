package com.thaonx.mockt3h.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thaonx.mockt3h.databinding.ItemNewsBinding
import com.thaonx.mockt3h.model.Articles
import com.thaonx.mockt3h.utils.Constants
import com.thaonx.mockt3h.view.fragment.news.MainNewsFragmentDirections

class ItemMainNewsAdapter : RecyclerView.Adapter<ItemMainNewsAdapter.ItemLatestViewHolder>() {

    private var binding: ItemNewsBinding? = null

    private val differUtils = object : DiffUtil.ItemCallback<Articles>() {
        override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differUtils)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemMainNewsAdapter.ItemLatestViewHolder {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemLatestViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ItemMainNewsAdapter.ItemLatestViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ItemLatestViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articles: Articles) {
            binding.article = articles
            Glide.with(itemView.context).load(articles.urlToImage).into(binding.imgLatest)
            binding.tvTimeNews.text = Constants.dateToTimeFormat(articles.publishedAt)
            binding.tvDateLatest.text = Constants.dateFormat(articles.publishedAt)

            itemView.setOnClickListener { mView ->
                val action = MainNewsFragmentDirections.actionMainNewsFragmentToDetailFragment(articles)
                mView.findNavController().navigate(action)
            }
        }
    }

}