package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.*

interface Auth {
    /**
     * Execute login for user
     *
     * @param email - User's email
     * @param password = User's password
     */
    @FormUrlEncoded
    @POST("/login")
    fun loginWithEmail(@Query("apikey") apikey: String,
                       @Field("email") email: String,
                       @Field("password") password: String) : Call<String>

    @GET("/user/{userId}")
    fun getUserData(@Path("userId") userId: Int,
                    @Query("apikey") apikey: String,
                    @Query("usertoken") userToken: String,
                    @Query("fields") fields: String) : Call<String>
}