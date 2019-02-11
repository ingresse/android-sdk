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
    fun updateBasicInfos(@Path("userId") userId: Int,
                         @Query("apikey") apikey: String,
                         @Query("usertoken") userToken: String,
                         @Body params: UserBasicInfos): Call<String>
}