package com.thaonx.mockt3h.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thaonx.mockt3h.databinding.WeatherLayoutBinding
import com.thaonx.mockt3h.model.WeatherItem
import com.thaonx.mockt3h.utils.Constants


class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private var binding: WeatherLayoutBinding? = null

    private val differUtil = object : DiffUtil.ItemCallback<WeatherItem>() {
        override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return oldItem == newItem
        }
    }

    var     differ = AsyncListDiffer(this, differUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WeatherAdapter.WeatherViewHolder {
        binding = WeatherLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: WeatherAdapter.WeatherViewHolder, position: Int) {
       holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class WeatherViewHolder(private val binding: WeatherLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherItem: WeatherItem) {
            val icon = weatherItem.weather[0].icon
            val urlImage = "${Constants.BASE_URL_IMAGE}$icon.png"

            Glide.with(itemView.context).load(urlImage)
                .into(binding.img)

            binding.tvDay.text = Constants.convertStringDay(weatherItem.dt)

            val tempMax = "${weatherItem.main.teamMax.toInt()}°"
            val tempMin = "${weatherItem.main.tempMin.toInt()}°"

            binding.tvTempMax.text = tempMax
            binding.tvTempMin.text = tempMin
        }
    }
}