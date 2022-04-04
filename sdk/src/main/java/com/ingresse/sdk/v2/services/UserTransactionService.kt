package com.ingresse.sdk.v2.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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

    /**
     * Refund transaction
     *
     * @param transactionId - transaction id
     * @param token - token of logged user
     * @param reason - reason for refund
     */
    @POST("/shop/{transactionId}/refund")
    suspend fun refundTransaction(
        @Path("transactionId") transactionId: String,
        @Query("apikey") apikey: String,
        @Query("usertoken") token: String,
        @Query("reason") reason: String
    ): Response<String>
}