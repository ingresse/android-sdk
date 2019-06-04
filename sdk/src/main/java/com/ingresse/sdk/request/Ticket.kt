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
     * Create or Refuse a ticket transfer
     *
     * @param ticketId - User id to get data
     * @param userToken - User token
     * @param user - User id or email to transfer
     * @param isReturn - flag for return
     */
    @POST("/ticket/{ticketId}/transfer")
    fun createTransfer(@Path("ticketId") ticketId: Long,
                       @Query("apikey") apikey: String,
                       @Query("usertoken") userToken: String,
                       @Field("user") user: String? = null,
                       @Field("isReturn") isReturn: Boolean? = false): Call<String>

    /**
     * Update a ticket transfer
     *
     * @param ticketId - Ticket id
     * @param transferId - Transfer id
     * @param userToken - User token
     * @param action - Actions to update a transfer
     */
    @POST("/ticket/{ticketId}/transfer/{transferId}")
    fun updateTransfer(@Path("ticketId") ticketId: Long,
                       @Path("transferId") transferId: Long,
                       @Query("apikey") apikey: String,
                       @Query("usertoken") userToken: String,
                       @Body params: TransferActionParams): Call<String>

}