package com.ingresse.sdk.request

import retrofit2.Call
import retrofit2.http.*

interface Refund {
    /**
     * Get refund reasons
     */
    @GET("/refundReasons")
    fun getRefundReasons(@Query("apikey") apikey: String) : Call<String>

    /**
     * Refund transaction
     *
     * @param transactionId - id from transaction
     * @param userToken - token from user
     * @param method - refund
     * @param reason - refund reason
     */
    @FormUrlEncoded
    @POST("/sale/{transactionId}")
    fun refundTransaction(@Path("transactionId") transactionId: String,
                          @Query("apikey") apikey: String,
                          @Query("usertoken") userToken: String,
                          @Query("method") method: String = "refund",
                          @Field("reason") reason: String): Call<String>
}