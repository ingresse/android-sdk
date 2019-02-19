package com.ingresse.sdk.request

import com.ingresse.sdk.model.request.TransactionParams
import retrofit2.Call
import retrofit2.http.*

interface Transaction {
    /**
     * Create transaction to get id for payment or reserve
     *
     * @param userToken - user token
     * @param params - params for transaction creation
     */
    @POST("/shop")
    fun createTransaction(@Query("apikey") apikey: String,
                          @Query("usertoken") userToken: String,
                          @Body params: TransactionParams): Call<String>

    /**
     * Get transaction details
     *
     * @param transactionId - transaction id
     * @param userToken - user token
     */
    @GET("/sale/{transactionId}")
    fun getTransactionDetails(@Path("transactionId") transactionId: String,
                              @Query("apikey") apikey: String,
                              @Query("usertoken") userToken: String): Call<String>

    /**
     * Cancel transaction
     *
     * @param transactionId - transaction id
     * @param userToken - user token
     */
    @POST("/shop/{transactionId}/cancel")
    fun cancelTransaction(@Path("transactionId") transactionId: String,
                          @Query("apikey") apikey: String,
                          @Query("usertoken") userToken: String): Call<String>
}