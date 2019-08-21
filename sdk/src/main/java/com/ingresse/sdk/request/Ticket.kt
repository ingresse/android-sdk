package com.ingresse.sdk.request

import com.ingresse.sdk.model.request.TransferActionParams
import retrofit2.Call
import retrofit2.http.*

interface Ticket {
    /**
     * Get Event Tickets
     *
     * @param eventId - id from selected event
     * @param sessionId - id from selected session
     */
    @GET("/event/{eventId}/session/{sessionId}/tickets")
    fun getEventTickets(@Path("eventId") eventId: String,
                        @Path("sessionId") sessionId: String,
                        @Query("apikey") apikey: String): Call<String>

    /**
     * 2FA Authentication user device
     *
     * @param userToken - user token
     * @header X-INGRESSE-OTP - code to validate user device
     * @header X-INGRESSE-DEVICE - uuid user device data
     */
    @POST("/two-factor")
    fun authenticationUserDevice(@Query("apikey") apikey: String,
                                 @Query("usertoken") userToken: String,
                                 @Header("X-INGRESSE-OTP") code: String? = null,
                                 @Header("X-INGRESSE-DEVICE") device: String? = null): Call<String>

    /**
     * Create or Refuse a ticket transfer
     *
     * @param ticketId - Ticket id to transfer
     * @param userToken - User token
     * @param user - User id or email to transfer
     * @param isReturn - flag for return
     */
    @FormUrlEncoded
    @POST("/ticket/{ticketId}/transfer")
    fun createTransfer(@Path("ticketId") ticketId: Long,
                       @Query("apikey") apikey: String,
                       @Query("usertoken") userToken: String,
                       @Field("user") user: String,
                       @Field("isReturn") isReturn: Boolean? = false): Call<String>

    /**
     * Update a ticket transfer
     *
     * @param ticketId - Ticket id to update transfer
     * @param transferId - Transfer id
     * @param userToken - User token
     * @param params - Param with action to update a transfer
     */
    @POST("/ticket/{ticketId}/transfer/{transferId}")
    fun updateTransfer(@Path("ticketId") ticketId: Long,
                       @Path("transferId") transferId: Long,
                       @Query("apikey") apikey: String,
                       @Query("usertoken") userToken: String,
                       @Body params: TransferActionParams): Call<String>
}