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
                    @Query("fields") fields: String): Call<String>

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
     * Gets user tickets. 
     *
     * @param userId - id from user
     * @param page - page from user ticket
     * @param pageSize - size of user ticket page
     * @param userToken - token from user
     */
    @GET("/user/{userId}/tickets")
    fun getUserTickets(@Path("userId") userId: String,
                       @Query("apikey") apikey: String,
                       @Query("page") page: Int? = null,
                       @Query("pageSize") pageSize: Int? = null,
                       @Query("usertoken") token: String): Call<String>
}