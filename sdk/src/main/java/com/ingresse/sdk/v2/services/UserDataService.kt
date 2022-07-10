package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.UpdateUserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserDataService {

    /**
     * Get user data
     *
     * @param userId - id from user
     * @param userToken - token from user
     * @param fields - desired fields separated by comma related to user
     */
    @GET("/user/{userId}")
    suspend fun getUserData(
        @Path("userId") userId: Int,
        @Query("apikey") apikey: String,
        @Query("usertoken") userToken: String,
        @Query("fields") fields: String?,
    ): Response<String>

    /**
     * Update user basic infos
     *
     * @param userId - id from user
     * @param userToken - token from user
     * @param params - object with infos to update
     */
    @POST("/user/{userId}")
    suspend fun updateUserData(
        @Path("userId") userId: String,
        @Query("apikey") apikey: String,
        @Query("usertoken") userToken: String,
        @Body params: UpdateUserData
    ): Response<String>
}
