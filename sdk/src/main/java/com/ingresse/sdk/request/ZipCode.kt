package com.ingresse.sdk.request

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

interface ZipCode {
    /**
     * Gets the complete address.
     *
     * @param zipCode Address zipcode.
     */
    @GET("/{zipcode}")
    fun getAddress(@Path("zipcode") zipCode: String): Call<String>
}