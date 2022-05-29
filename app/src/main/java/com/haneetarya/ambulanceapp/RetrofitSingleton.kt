package com.haneetarya.ambulanceapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSingleton {
    companion object{
        const val baseUrl = "https://abulance-backend.herokuapp.com/"
        private fun getInstance(): OkHttpClient = OkHttpClient().newBuilder().build()
        private val retrofit: Retrofit = Retrofit.Builder().baseUrl(baseUrl).client(getInstance())
            .addConverterFactory(GsonConverterFactory.create()).build()

        val networkCalls: NetworkCalls = retrofit.create(NetworkCalls::class.java)
    }

}