package com.thaonx.mockt3h.view.fragment.news

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import com.thaonx.mockt3h.R
import com.thaonx.mockt3h.adapter.ItemHomeAdapter
import com.thaonx.mockt3h.base.BaseFragment
import com.thaonx.mockt3h.databinding.FragmentHomeBinding
import com.thaonx.mockt3h.utils.Constants
import com.thaonx.mockt3h.view.activity.LoginActivity
import com.thaonx.mockt3h.viewmodel.ItemViewModel
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
), LocationListener, SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private val itemHomeAdapter = ItemHomeAdapter()
    private val itemViewModel: ItemViewModel by viewModels()

    private var urlImage = ""
    private var tempCurrent = ""

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var locationManager: LocationManager? = null
    private var city = ""

    private lateinit var preferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Toast.makeText(requireActivity(),
                    Constants.NOTIFY_LOCATION,
                    Toast.LENGTH_SHORT).show()
                locationManager =
                    activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                getLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Toast.makeText(requireActivity(),
                    "Access coarse location is granted",
                    Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(requireActivity(),
                    Constants.NOTIFY_LOCATION_NOT_GRANTED,
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun obServerLivedata() {

        itemViewModel.itemNewsResponse.observe(viewLifecycleOwner) { itemHome ->

            if (itemHome != null) {
                itemHomeAdapter.differ.submitList(itemHome.articles)
                swipeRefreshLayout.isRefreshing = false
            }
        }

        itemViewModel.weatherResponse.observe(viewLifecycleOwner) { weather ->
            val icon = weather.list[0].weather[0].icon
            urlImage = "${Constants.BASE_URL_IMAGE}$icon.png"

            tempCurrent = weather.list[0].main.tempMin.toInt().toString() + "°C"

            Glide.with(requireContext()).load(urlImage).into(binding.imgWeather)
            binding.tvTempCurrentHome.text = tempCurrent
        }
    }

    override fun initData() {
        itemViewModel.getAllNews(Constants.Q_HOME)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        setHasOptionsMenu(true)

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

        setupSwipeRefreshLayout()
        showFragmentWeatherDialog()

        preferences =
            requireActivity().getSharedPreferences(LoginActivity.SHARED_PREF, Context.MODE_PRIVATE)

        val username = preferences.getString(LoginActivity.KEY_USERNAME, "")

        binding.tvTitle.text = Constants.TITLE_FRAGMENT_HOME + "$username"

    }



    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout = binding.swipeRefreshLayout

        //swipe update data
        swipeRefreshLayout.setOnRefreshListener {
            obServerLivedata()
        }

        swipeRefreshLayout.setColorSchemeColors(
            Color.BLUE
        )

        //start fragment
        swipeRefreshLayout.post {
            swipeRefreshLayout.isRefreshing = true
            setupRecyclerview()
        }
    }

    private fun showFragmentWeatherDialog() {
        binding.imgWeather.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_weatherFragment)
        }
    }

    private fun setupRecyclerview() {
        binding.rvHome.apply {
            adapter = itemHomeAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
            500,
            5F,
            this as LocationListener)
    }


    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        city = addresses[0].adminArea

        itemViewModel.getAllItemWeather(city)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                true
            }

            R.id.actionLogOut -> {
                val editor = preferences.edit()
                editor.clear()
                editor.apply()

                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_left)
                requireActivity().finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(query)
        findNavController().navigate(action)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return false
    }
}