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

    /**
     * Execute login for user
     *
     * @param email - User's email
     * @param password - User's password
     */
    @FormUrlEncoded
    @POST("/login")
    fun login(@Query("apikey") apikey: String,
              @Field("email") email: String,
              @Field("password") password: String) : Call<String>

    /**
     * Execute login with Facebook data retrieved from Facebook SDK
     *
     * @param email - Facebook user email
     * @param fbToken - Facebook token
     * @param fbUserId - Facebook user id
     */
    @FormUrlEncoded
    @POST("/login/facebook")
    fun loginWithFacebook(@Query("apikey") apikey: String,
                          @Field("email") email: String,
                          @Field("fbToken") fbToken: String,
                          @Field("fbUserId") fbUserId: String) : Call<String>

    /**
     * Renew user auth token
     *
     * @param userToken - Token of logged user
     */
    @GET("/login/renew-token")
    fun renewAuthToken(@Query("apikey") apikey: String,
                       @Query("usertoken") userToken: String) : Call<String>

    /**
     * Recover user password
     *
     * @param email - user email
     */
    @FormUrlEncoded
    @POST("/recover-password")
    fun recoverPassword(@Query("apikey") apikey: String,
                        @Field("email") email: String) : Call<String>

    /**
     * Validate hash from email sent in recover password action
     *
     * @param email - user email
     * @param hash - hash received from email
     */
    @FormUrlEncoded
    @POST("/recover-validate")
    fun validateHash(@Query("apikey") apikey: String,
                     @Field("email") email: String,
                     @Field("hash") hash: String) : Call<String>

    /**
     * Update user password
     *
     * @param email - user email
     * @param password - password to update
     * @param hash - hash received from email
     */
    @FormUrlEncoded
    @POST("/recover-update-password")
    fun updatePassword(@Query("apikey") apikey: String,
                       @Field("email") email: String,
                       @Field("password") password: String,
                       @Field("hash") hash: String) : Call<String>
}