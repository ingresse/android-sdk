package com.ingresse.sdk.request

import com.ingresse.sdk.model.request.FreeTicket
import com.ingresse.sdk.model.request.Payment
import com.ingresse.sdk.model.request.TransactionParams
import retrofit2.Call
import retrofit2.http.*

interface Payment {
    /**
     * Create transaction to get id for payment or reserve
     *
     * @param userToken - user token
     * @param params - params for transaction creation
     */
    @POST("/shop")
    fun createTransaction(@Query("apikey") apikey: String,
                          @Query("usertoken") userToken: String,
                          @Body body: TransactionParams): Call<String>

    /**
     * Reserve ticket in payment
     *
     * @param userToken - token of logged user
     * @param body - attributes to reserve ticket
     */
    @POST("/shop")
    fun doReserve(@Query("apikey") apikey: String,
                  @Query("usertoken") userToken: String,
                  @Body body: FreeTicket): Call<String>

    /**
     * Payment tickets
     *
     * @param userToken - token of logged user
     * @param body - attributes to payment ticket
     */
    @POST("/shop")
    fun doPayment(@Query("apikey") apikey: String,
                  @Query("usertoken") userToken: String,
                  @Body body: Payment): Call<String>
}