package com.example.myapplication

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object DataFetcher {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/products")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()





}