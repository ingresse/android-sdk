package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface User {
    /**
     * Get user data
     *
     * @param userId - id from user
     * @param userToken - token from user
     * @param fields - desired fields to get data
     */
    @GET("/user/{userId}")
    fun getUserData(@Path("userId") userId: Int,
                    @Query("apikey") apikey: String,
                    @Query("usertoken") userToken: String,
                    @Query("fields") fields: String) : Call<String>
}