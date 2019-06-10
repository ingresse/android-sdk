package com.ingresse.sdk.request

import com.ingresse.sdk.helper.REFUND_METHOD_KEY
import com.ingresse.sdk.model.request.TransactionParams
import com.ingresse.sdk.model.request.TransactionStatus
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

    /**
     * Get transaction report from specific event
     *
     * @param eventId - event id
     * @param userToken - user token
     */
    @GET("/dashboard/{eventId}/transactionReport")
    fun getTransactionReport(@Path("eventId") eventId: Int,
                             @Query("apikey") apikey: String,
                             @Query("usertoken") userToken: String): Call<String>

    /**
     * Get transaction list from specific event with optional filters
     *
     * @param eventId - event id
     * @param userToken - user token
     * @param page - curent page
     * @param from - search filter by date
     * @param to - search filter by date
     * @param term - term for search
     * @param status - filter by status
     */
    @GET("/sale")
    fun getTransactionList(@Query("event") eventId: String,
                           @Query("apikey") apikey: String,
                           @Query("usertoken") userToken: String,
                           @Query("page") page: Int? = null,
                           @Query("from") from: String? = null,
                           @Query("to") to: String? = null,
                           @Query("term") term: String? = null,
                           @Query("status") status: TransactionStatus? = null): Call<String>

    /**
     * Get transaction list with optional filters including event id
     *
     * @param eventId - event id
     * @param userToken - user token
     * @param term - term for search in tid, transactionId, user name, email, document, nsu
     * @param from - filter transactions that happened equal or after this time
     * @param to - filter transactions that happened equal or before this time
     * @param acquirer - acquirer
     * @param nsu - payment identification by nsu
     * @param amount - transaction total value
     * @param cardFirstDigits - customer`s credit card first 6 digits
     * @param cardLastDigits - customer`s credit card last 4 digits
     * @param status - transaction status
     * @param page - current page
     * @param pageSize - results size
     * @param order - order transactions by date of creation by ASC or DESC
     */
    @GET("/transactions")
    fun getTransactions(@Query("apikey") apikey: String,
                        @Query("usertoken") userToken: String,
                        @Query("event") eventId: String? = null,
                        @Query("term") term: String? = null,
                        @Query("from") from: String? = null,
                        @Query("to") to: String? = null,
                        @Query("acquirer") acquirer: String? = null,
                        @Query("nsu") nsu: String? = null,
                        @Query("amount") amount: Int? = null,
                        @Query("card_first_digits") cardFirstDigits: Int? = null,
                        @Query("card_last_digits") cardLastDigits: Int? = null,
                        @Query("status") status: String? = null,
                        @Query("page") page: Int? = null,
                        @Query("pageSize") pageSize: Int? = null,
                        @Query("order") order: String? = null): Call<String>

    /**
     * Get details from a specific transaction
     *
     * @param transactionId - id from some transaction
     * @param userToken - user token
     */
    @GET("/transaction/{transactionId}")
    fun getDetails(@Path("transactionId") transactionId: String,
                   @Query("apikey") apikey: String,
                   @Query("usertoken") userToken: String): Call<String>

    /**
     * Get refund reasons
     */
    @GET("/refundReasons")
    fun getRefundReasons(@Query("apikey") apikey: String) : Call<String>

    /**
     * Refund a specific transaction (Ticket refund)
     *
     * @param transactionId - id from some transaction
     * @param userToken - user token
     * @param method - fixed method: refund
     * @param reason - refund reason
     */
    @FormUrlEncoded
    @POST("/sale/{transactionId}")
    fun refundTransaction(@Path("transactionId") transactionId: String,
                          @Query("apikey") apikey: String,
                          @Query("usertoken") userToken: String,
                          @Query("method") method: String = REFUND_METHOD_KEY,
                          @Field("reason") reason: String): Call<String>
}