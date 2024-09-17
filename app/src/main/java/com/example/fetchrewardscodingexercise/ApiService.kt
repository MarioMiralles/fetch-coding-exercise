package com.example.fetchrewardscodingexercise // This should already match your package name

import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}