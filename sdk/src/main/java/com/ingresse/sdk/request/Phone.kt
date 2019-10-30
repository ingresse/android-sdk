package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET

interface Phone {
    /**
     * Get contry list for ddis
     */
    @GET("/country")
    fun getCountryList(): Call<String>
}