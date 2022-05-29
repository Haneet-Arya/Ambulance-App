package com.haneetarya.ambulanceapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkCalls {

    @GET("hospital")
    fun getHospitals(): Call<ArrayList<HospitalModel>>

}