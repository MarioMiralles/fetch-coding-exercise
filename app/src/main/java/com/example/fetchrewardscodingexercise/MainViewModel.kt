package com.example.fetchrewardscodingexercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    private val apiService = Retrofit.Builder()
        .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            try {
                val fetchedItems = apiService.getItems()
                _items.value = fetchedItems
                    .filter { !it.name.isNullOrBlank() }
                    .sortedWith(compareBy({ it.listId }, { it.name }))
            } catch (e: Exception) {
                // Handle error
                println("Error fetching items: ${e.message}")
            }
        }
    }
}