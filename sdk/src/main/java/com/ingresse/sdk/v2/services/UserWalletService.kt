package com.ingresse.sdk.v2.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@Suppress("LongParameterList")
interface UserWalletService {
    /**
     * Gets user tickets
     *
     * @param userId - id from user
     * @param token - token from user
     * @param page - current page
     * @param pageSize - size of response
     * @param eventId - specify event tickets by event id
     */
    @GET("/user/{userId}/tickets")
    suspend fun getUserTickets(
        @Path("userId") userId: String,
        @Query("apikey") apikey: String,
        @Query("usertoken") token: String,
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int?,
        @Query("eventId") eventId: Int?,
    ): Response<String>
}
