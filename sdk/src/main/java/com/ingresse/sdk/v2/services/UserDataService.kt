package com.ingresse.sdk.v2.services

import retrofit2.Response
import retrofit2.http.GET
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
}
