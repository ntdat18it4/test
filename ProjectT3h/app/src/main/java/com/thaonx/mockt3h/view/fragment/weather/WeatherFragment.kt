package com.thaonx.mockt3h.view.fragment.weather

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import android.location.LocationManager
import com.thaonx.mockt3h.R
import com.thaonx.mockt3h.adapter.WeatherAdapter
import com.thaonx.mockt3h.databinding.FragmentWeatherBinding
import com.thaonx.mockt3h.utils.Constants
import com.thaonx.mockt3h.viewmodel.ItemViewModel
import java.util.*


@AndroidEntryPoint
class WeatherFragment : BottomSheetDialogFragment(), LocationListener {

    companion object {
        const val MAX_RESULT = 1
    }

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private var weatherAdapter: WeatherAdapter = WeatherAdapter()
    private val itemViewModel: ItemViewModel by viewModels()

    private var locationManager: LocationManager? = null
    private var city = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        getLocation()

    }

    private fun obServerLiveData() {
        itemViewModel.weatherResponse.observe(viewLifecycleOwner) { weather ->
            weatherAdapter.differ.submitList(weather.list)

            binding.tempCurrent.text = weather.list[0].main.tempMin.toInt().toString()
            binding.tvCity.text = city
            binding.temp.setText(R.string.temp)
            binding.tvSourceWeather.setText(R.string.source_weather)

            val icon = weather.list[0].weather[0].icon
            val urlImage = "${Constants.BASE_URL_IMAGE}$icon.png"

            Glide.with(requireActivity()).load(urlImage)
                .into(binding.imgWF)
            binding.progressbar.visibility = View.GONE
        }
    }

    private fun getData() {
        itemViewModel.getAllItemWeather(city)
    }

    private fun initView() {
        setupRecyclerview()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
            500,
            5F,
            this as LocationListener)
    }

    private fun setupRecyclerview() {
        binding.rvWeather.apply {
            adapter = weatherAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, MAX_RESULT)

        city = addresses[0].adminArea

        obServerLiveData()
        getData()
        initView()
    }
}