package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.UpdateUserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserDataService {

    /**
     * Get user data
     *
     * @param userId - id from user
     * @param userToken - token from user
     */
    @GET("/users/{userId}")
    suspend fun getUserData(
        @Path("userId") userId: Int,
        @Query("apikey") apikey: String,
        @Query("usertoken") userToken: String
    ): Response<String>

    /**
     * Update user basic infos
     *
     * @param userId - id from user
     * @param userToken - token from user
     * @param params - object with infos to update
     */
    @PUT("/users/{userId}")
    suspend fun updateUserData(
        @Path("userId") userId: Int,
        @Query("apikey") apikey: String,
        @Query("usertoken") userToken: String,
        @Body params: UpdateUserData.Params
    ): Response<Void>
}
