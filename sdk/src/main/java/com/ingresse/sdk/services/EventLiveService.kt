package com.ingresse.sdk.services

import com.ingresse.sdk.IngresseClient
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
                        liveEventId : String,
                        onSuccess: (String) -> Unit,
                        onError: (APIError) -> Unit) {

        val liveUrl = URLBuilder(Host.LIVE, client.environment)
                .addParameter(key = "userToken", value = userToken)
                .addParameter(key = "liveEventId", value = liveEventId)

        return try {
            val request = liveUrl.build()
            onSuccess(request)
        } catch (ex: Exception) {
            onError(APIError.default)
        }
    }

}