package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Permission {
    /**
     * Get user sales group
     *
     * @param userToken - Token of logged user
     */
    @GET("/salesgroup")
    fun getSalesGroup(@Query("apikey") apikey: String,
                      @Query("usertoken") userToken: String) : Call<String>
}