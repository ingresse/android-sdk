package com.ingresse.sdk.services

import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import java.lang.Exception

class EventLiveService(private val client: IngresseClient) {

    /**
     * Get event live url
     *
     * @param userToken - user token
     * @param liveEventId - id live event
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getEventLiveUrl(userToken: String,
                        ticketId : Int,
                        onSuccess: (String) -> Unit,
                        onError: (APIError) -> Unit) {

        val liveUrl = URLBuilder(Host.LIVE, client.environment)
                .addParameter(key = "userToken", value = userToken)
                .addParameter(key = "ticketCode", value = ticketId.toString())

        return try {
            val request = liveUrl.paramsBuild()
            onSuccess(request)
        } catch (ex: Exception) {
            onError(APIError.default)
        }
    }

}