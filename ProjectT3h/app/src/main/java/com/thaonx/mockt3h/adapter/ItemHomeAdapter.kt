package com.thaonx.mockt3h.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thaonx.mockt3h.databinding.ItemHomeBinding
import com.thaonx.mockt3h.model.Articles
import com.thaonx.mockt3h.utils.Constants
import com.thaonx.mockt3h.view.fragment.news.HomeFragmentDirections

class ItemHomeAdapter : RecyclerView.Adapter<ItemHomeAdapter.ItemHomeViewHolder>() {

    private var binding: ItemHomeBinding? = null

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
    ): ItemHomeAdapter.ItemHomeViewHolder {
        binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHomeViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ItemHomeAdapter.ItemHomeViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ItemHomeViewHolder(private val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articles: Articles) {

           binding.article = articles

            Glide.with(itemView.context).load(articles.urlToImage).into(binding.imgHome)
            binding.tvTimeHome.text = Constants.dateToTimeFormat(articles.publishedAt)


            itemView.setOnClickListener { mView ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(articles)
                mView.findNavController().navigate(action)
            }
        }
    }
}