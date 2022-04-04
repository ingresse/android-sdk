package com.ingresse.sdk.v2.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserTransactionService {

    /**
     * Get user transactions
     *
     * @param token - token of logged user
     * @param status - status to filter
     * @param page - current page
     * @param pageSize - size of response
     */
    @GET("/user/transactions")
    suspend fun getUserTransactions(
        @Query("apikey") apikey: String,
        @Query("usertoken") token: String,
        @Query("status") status: String?,
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int?
    ): Response<String>
}