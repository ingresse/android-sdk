package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Phone {
    /**
     * Get contry list for ddis
     */
    @GET("/country")
    fun getCountryList(@Query("apikey") apikey: String): Call<String>
}