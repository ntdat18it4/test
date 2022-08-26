package com.thaonx.mockt3h.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.thaonx.mockt3h.model.ArticlesFavorite
import com.thaonx.mockt3h.model.Covid
import com.thaonx.mockt3h.model.Item
import com.thaonx.mockt3h.model.Weather
import com.thaonx.mockt3h.repository.IItemRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemRepository: IItemRepository,
) : ViewModel() {
    //network

    private val _itemNewsResponse: MutableLiveData<Item> = MutableLiveData()
    val itemNewsResponse
        get() = _itemNewsResponse

    private val _covidResponse: MutableLiveData<Covid> = MutableLiveData()
    val covidResponse
        get() = _covidResponse

    private val _weatherResponse: MutableLiveData<Weather> = MutableLiveData()
    val weatherResponse
        get() = _weatherResponse

    //database

    private val _articleFavorite: MutableLiveData<List<ArticlesFavorite>> = MutableLiveData()
    val articlesFavorite
        get() = _articleFavorite

    //network fun

    fun getAllNews(q: String) {
        viewModelScope.launch {
            _itemNewsResponse.value = itemRepository.getAllNews(q)
        }
    }

    fun getAllCovid() {
        viewModelScope.launch {
            _covidResponse.value = itemRepository.getAllCovid()
        }
    }

    fun getAllItemWeather(city: String) {
        viewModelScope.launch {
            _weatherResponse.value = itemRepository.getAllItemWeather(city)
        }
    }

    //database fun

    fun insertArticleFavorite(articlesFavorite: ArticlesFavorite) {
        viewModelScope.launch {
            itemRepository.insertArticleFavorite(articlesFavorite)
        }
    }

    fun deleteArticleFavorite(articlesFavorite: ArticlesFavorite) {
        viewModelScope.launch {
            itemRepository.deleteArticleFavorite(articlesFavorite)
        }
    }

    fun getAllArticleFavorite() {
        viewModelScope.launch {
            itemRepository.getAllArticleFavorite().catch { ex ->
                Log.e("fresher", "getAllNotes Exception : $ex")
                _articleFavorite.value = arrayListOf()
            }.collect { value ->
                _articleFavorite.value = value
            }
        }
    }
}