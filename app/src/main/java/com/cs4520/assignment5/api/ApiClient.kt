package com.cs4520.assignment5.api

import com.cs4520.assignment5.Product
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("prod/random")
    fun fetchData(): Call<JsonElement>

    @GET("prod/random")
    suspend fun fetchDataSuspend(): JsonElement
}