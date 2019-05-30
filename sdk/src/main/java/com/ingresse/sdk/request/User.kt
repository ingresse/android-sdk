package com.ingresse.sdk.request

import com.ingresse.sdk.model.request.UserBasicInfos
import retrofit2.Call
import retrofit2.http.*

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

    /**
     * Update user basic infos
     *
     * @param userId - id from user
     * @param userToken - token from user
     * @param params - object with infos to update
     */
    @POST("/user/{userId}")
    fun updateBasicInfos(@Path("userId") userId: String,
                         @Query("apikey") apikey: String,
                         @Query("usertoken") userToken: String,
                         @Body params: UserBasicInfos): Call<String>

    /**
     * Gets wallet user events.
     *
     * @param userId id of logged user.
     * @param token of logged user.
     * @param page chosen by user.
     * @param pageSize chosen by user.
     */
    @GET("/user/{userId}/wallet")
    fun getWalletEvents(@Path("userId") userId: String,
                        @Query("apikey") apikey: String,
                        @Query("usertoken") token: String,
                        @Query("page") page: Int? = null,
                        @Query("pageSize") pageSize: Int? = null,
                        @Query("from") dateFrom: String? = null,
                        @Query("to") dateTo: String? = null): Call<String>
}