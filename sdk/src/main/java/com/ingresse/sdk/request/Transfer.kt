package com.ingresse.sdk.request

import com.ingresse.sdk.model.request.ReturnTicketType
import com.ingresse.sdk.model.request.TransferActionParams
import com.ingresse.sdk.model.request.UserToTransfer
import retrofit2.Call
import retrofit2.http.*

interface Transfer {
    /**
     * Gets user transfers.
     *
     * @param userId id from user
     * @param page - page from user ticket
     * @param pageSize - size of user ticket page
     * @param usertoken - token from user
     * @param status - user transfers status
     */
    @GET("/user/{userId}/transfers")
    fun getUserTransfers(@Path("userId") userId: String,
                         @Query("apikey") apikey: String,
                         @Query("page") page: Int? = null,
                         @Query("pageSize") pageSize: Int? = null,
                         @Query("usertoken") token: String,
                         @Query("status") status: String): Call<String>

    /**
     * Gets user recent transfers.
     *
     * @param userId id of the user.
     * @param userToken - User token
     * @param order - order control for the result
     * @param size - result page size
     */
    @GET("/user/{userId}/last-transfers")
    fun getRecentTransfers(@Path("userId") userId: String,
                           @Query("apikey") apikey: String,
                           @Query("usertoken") userToken: String,
                           @Query("order") order: String?,
                           @Query("size") size: Int?): Call<String>

    /**
     * Search friends by name
     *
     * @param term - String term to search for
     * @param size - Number of results
     * @param userToken - User token
     */
    @GET("/search/transfer/user")
    fun getFriendsFromSearch(@Query("term") term: String,
                             @Query("size") size: String?,
                             @Query("apikey") apikey: String,
                             @Query("usertoken") userToken: String): Call<String>

    /**
     * Update a ticket transfer
     *
     * @param ticketId - Ticket id to update transfer
     * @param transferId - Transfer id
     * @param userToken - User token
     * @param params - Param with action to update a transfer
     */
    @POST("/ticket/{ticketId}/transfer/{transferId}")
    fun updateTransfer(@Path("ticketId") ticketId: Int,
                       @Path("transferId") transferId: Int,
                       @Query("apikey") apikey: String,
                       @Query("usertoken") userToken: String,
                       @Body params: TransferActionParams): Call<String>

    /**
     * Return a ticket
     *
     * @param ticketId - Ticket id to update transfer
     * @param userToken - User token
     * @param type - for isReturn param
     */
    @POST("/ticket/{ticketId}/transfer")
    fun returnTicket(@Path("ticketId") ticketId: Int,
                     @Query("apikey") apikey: String,
                     @Query("usertoken") userToken: String,
                     @Body type: ReturnTicketType = ReturnTicketType()): Call<String>

    /**
     * Transfer a ticket
     *
     * @param ticketId - Ticket id to transfer
     * @param userToken - User token
     * @param toUser - user id or e-mail to transfer
     */
    @POST("/ticket/{ticketId}/transfer")
    fun transferTicket(@Path("ticketId") ticketId: Int,
                       @Query("apikey") apikey: String,
                       @Query("usertoken") userToken: String,
                       @Body user: UserToTransfer) : Call<String>
}