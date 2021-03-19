package com.ingresse.sdk.v2.services

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    /**
     * Execute Company login for user
     *
     * @param email - User's email
     * @param password - User's password
     */
    @FormUrlEncoded
    @POST("/company-login")
    suspend fun companyLoginWithEmail(
        @Query("apikey") apikey: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<String>

    /**
     * Execute login for user
     *
     * @param email - User's email
     * @param password - User's password
     */
    @FormUrlEncoded
    @POST("/login")
    suspend fun login(
        @Query("apikey") apikey: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<String>

    /**
     * Execute login with Facebook data retrieved from Facebook SDK
     *
     * @param email - Facebook user email
     * @param facebookToken - Facebook token
     * @param facebookUserId - Facebook user id
     */
    @FormUrlEncoded
    @POST("/login/facebook")
    suspend fun loginWithFacebook(
        @Query("apikey") apikey: String,
        @Field("email") email: String,
        @Field("fbToken") facebookToken: String,
        @Field("fbUserId") facebookUserId: String,
    ): Response<String>

    /**
     * Renew user auth token
     *
     * @param token - Token of logged user
     */
    @GET("/login/renew-token")
    suspend fun renewAuthToken(
        @Query("apikey") apikey: String,
        @Query("usertoken") token: String,
    ): Response<String>
}
