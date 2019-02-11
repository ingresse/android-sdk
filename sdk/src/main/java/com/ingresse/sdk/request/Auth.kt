package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.*

interface Auth {
    /**
     * Execute Company login for user
     *
     * @param email - User's email
     * @param password - User's password
     */
    @FormUrlEncoded
    @POST("/company-login")
    fun companyLoginWithEmail(@Query("apikey") apikey: String,
                       @Field("email") email: String,
                       @Field("password") password: String) : Call<String>
}