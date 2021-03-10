package com.ingresse.sdk.v2.services

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface PasswordService {

    /**
     * Recover user password
     *
     * @param email - user email
     */
    @FormUrlEncoded
    @POST("/recover-password")
    suspend fun requestReset(
        @Query("apikey") apikey: String,
        @Field("email") email: String,
    ): Response<String>

    /**
     * Validate hash from email sent in recover password action
     *
     * @param email - user email
     * @param hash - hash received from email
     */
    @FormUrlEncoded
    @POST("/recover-validate")
    suspend fun validateHash(
        @Query("apikey") apikey: String,
        @Field("email") email: String,
        @Field("hash") hash: String,
    ): Response<String>

    /**
     * Update user password
     *
     * @param email - user email
     * @param password - password to update
     * @param hash - hash received from email
     */
    @FormUrlEncoded
    @POST("/recover-update-password")
    suspend fun updatePassword(
        @Query("apikey") apikey: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("hash") hash: String,
    ): Response<String>
}
