package com.ingresse.sdk.v2.services

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface PasswordStrengthService {

    /**
     * Validate password strength
     *
     * @param password - password to be validated
     */
    @POST("/password")
    suspend fun validatePasswordStrength(
        @Query("apikey") apikey: String,
        @Query("password") password: String,
    ): Response<String>
}
