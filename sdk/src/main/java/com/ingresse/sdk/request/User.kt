package com.ingresse.sdk.request

import com.ingresse.sdk.model.request.*
import retrofit2.Call
import retrofit2.http.*

interface User {
    /**
     * Get user data
     *
     * @param userId - id from user
     * @param userToken - token from user
     * @param fields - desired fields to get data
     */
    @GET("/user/{userId}")
    fun getUserData(@Path("userId") userId: Int,
                    @Query("apikey") apikey: String,
                    @Query("usertoken") userToken: String,
                    @Query("fields") fields: String): Call<String>

    /**
     * Update user basic infos
     *
     * @param userId - id from user
     * @param userToken - token from user
     * @param params - object with infos to update
     */
    @POST("/user/{userId}")
    fun updateBasicInfos(@Path("userId") userId: String,
                         @Query("apikey") apikey: String,
                         @Query("usertoken") userToken: String,
                         @Body params: UserBasicInfos): Call<String>

    /**
     * Update user picture
     *
     * @param userId - id from user
     */
    @POST("/user/{userId}")
    fun updateUserPicture(@Path("userId") userId: String,
                         @Query("apikey") apikey: String,
                         @Query("usertoken") userToken: String,
                         @Body params: UserPicture): Call<String>
                 
    /**
     * Update user address
     *
     * @param userId - id from users
     * @param userToken - token from user
     * @param params - object with infos to update
     */
    @POST("/user/{userId}")
    fun updateUserAddress(@Path("userId") userId: String,
                          @Query("apikey") apikey: String,
                          @Query("usertoken") userToken: String,
                          @Body params: UserAddressInfos): Call<String>

    /**
     * Update user address
     *
     * @param userId - id from user
     * @param method - info request action
     * @param userToken - token from user
     * @param params - object with infos to update
     */
    @POST("/user/{userId}")
    fun updateUserAddress(@Path("userId") userId: String,
                          @Query("method") method: String,
                          @Query("apikey") apikey: String,
                          @Query("usertoken") userToken: String,
                          @Body params: UserAddressInfos): Call<String>

    /**
     * Update planner infos
     *
     * @param userId - id from user
     * @param userToken - token from user
     * @param params - planner fields to update
     */
    @POST("/user/{userId}")
    fun updateUserPlannerInfos(@Path("userId") userId: String,
                               @Query("apikey") apikey: String,
                               @Query("usertoken") userToken: String,
                               @Body params: UserPlanner): Call<String>

    /**
     * Get cash closing for specified date offset
     *
     * @param userId - id of the operator to get info
     * @param from - start date
     * @param to - end date
     * @param userToken - token from user
     */
    @GET("/balance")
    fun getCashClosing(@Query("apikey") apikey: String,
                       @Query("operator") userId: String,
                       @Query("from") from: String,
                       @Query("to") to: String,
                       @Query("usertoken") userToken: String): Call<String>

    /**
     * Gets user tickets
     *
     * @param userId - id from user
     * @param page - page from user ticket
     * @param pageSize - size of user ticket page
     * @param token - token from user
     */
    @GET("/user/{userId}/tickets")
    fun getUserTickets(@Path("userId") userId: String,
                       @Query("apikey") apikey: String,
                       @Query("page") page: Int? = null,
                       @Query("pageSize") pageSize: Int? = null,
                       @Query("eventId") eventId: Int? = null,
                       @Query("usertoken") token: String): Call<String>

    /**
     * Get user event attributes
     *
     * @param eventId - id from event
     * @param userToken - token from user
     * @param filters - event filter
     */
    @GET("/event/{eventId}/attributes")
    fun getEventAttributes(@Path("eventId") eventId: String,
                           @Query("apikey") apikey: String,
                           @Query("usertoken") userToken: String? = null,
                           @Query("filters") filters: String? = null): Call<String>

    /**
     * Gets wallet user events.
     *
     * @param userId id of logged user.
     * @param token of logged user.
     * @param page chosen by user.
     * @param pageSize chosen by user.
     */
    @GET("/user/{userId}/wallet")
    fun getWalletEvents(@Path("userId") userId: String,
                        @Query("apikey") apikey: String,
                        @Query("usertoken") token: String,
                        @Query("page") page: Int? = null,
                        @Query("pageSize") pageSize: Int? = null,
                        @Query("from") dateFrom: String? = null,
                        @Query("to") dateTo: String? = null): Call<String>

    /**
     * Validate password strength
     *
     * @param password - password to be validated
     */
    @POST("/password")
    fun validatePasswordStrength(@Query("password") password: String,
                                 @Query("apikey") apikey: String): Call<String>

    /**
     * Change password
     *
     * @param userId - id from user
     * @param userToken - token from user
     * @param params - old and new password to change
     */
    @POST("/user/{userId}")
    fun changePassword(@Path("userId") userId: String,
                       @Query("usertoken") usertoken: String,
                       @Query("apikey") apikey: String,
                       @Body params: PasswordInfo): Call<String>

    /**
     * Create account
     *
     * @param name - name
     * @param lastName - last name
     * @param email - email
     * @param password - password
     * @param document - optional document
     * @param ddi - phone ddi
     * @param phone - complete phone
     * @param newsletter - sign newsletter
     */
    @FormUrlEncoded
    @POST("user")
    fun createAccount(@Field("name") name: String,
                      @Field("lastName") lastName: String,
                      @Field("email") email: String,
                      @Field("password") password: String,
                      @Field("document") document: String?,
                      @Field("ddi") ddi: String,
                      @Field("phone") phone: String,
                      @Field("news") newsletter: Boolean,
                      @Query("apikey") apikey: String): Call<String>
}