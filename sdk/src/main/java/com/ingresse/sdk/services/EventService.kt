package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.*
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.ErrorBlock
import com.ingresse.sdk.helper.CANCELED_CALL
import com.ingresse.sdk.helper.EXPIRED
import com.ingresse.sdk.helper.SOCKET_CLOSED
import com.ingresse.sdk.model.request.EventListByProducer
import com.ingresse.sdk.model.response.EventJSON
import com.ingresse.sdk.model.response.EventStaffJSON
import com.ingresse.sdk.request.Event
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class EventService(private val client: IngresseClient) {
    private val service: Event
    private var cancelAllCalled = false

    private var mGetEventListByProducerCall: Call<String>? = null
    private var mGetEventDescriptionCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(URLBuilder(Host.EVENTS, client.environment, client.customPrefix).build())
                .build()

        service = adapter.create(Event::class.java)
    }

    /**
     * Method to cancel all requests
     */
    fun cancelAll() {
        cancelAllCalled = true
        mGetEventListByProducerCall?.cancel()
        mGetEventDescriptionCall?.cancel()
        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Method to cancel a get event list by producer
     */
    fun cancelGetEventListByProducer(concurrent: Boolean = false) {
        if (!concurrent) {
            mGetEventListByProducerCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Event list by producer
     *
     * @param concurrent - flag to concurrent request
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onTokenExpired - token expired callback
     * @param onConnectionError - connection error callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "Event(client).getProducerEventList(request = SearchEvents())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.SearchEvents",
            ]
        )
    )
    fun getEventListByProducer(concurrent: Boolean = false,
                               request: EventListByProducer? = EventListByProducer(),
                               onSuccess: (Pair<ArrayList<Source<EventJSON>>, Int>) -> Unit,
                               onError: ErrorBlock,
                               onCanceledCall: Block? = null,
                               onTokenExpired: Block,
                               onConnectionError: (error: Throwable) -> Unit) {

        val call  = service.getEventListByProducer(
            title = request?.title,
            size = request?.size,
            orderBy = request?.orderBy,
            from = request?.from,
            to = request?.to,
            offset = request?.offset
        )

        if (!concurrent) mGetEventListByProducerCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<ResponseHits<EventJSON>?> {
            override fun onSuccess(data: ResponseHits<EventJSON>?) {
                if (cancelAllCalled) return

                val response = data?.data?.hits ?: return onError(APIError.default)

                if (!concurrent) mGetEventListByProducerCall = null else mConcurrentCalls.remove(call)
                onSuccess(Pair(response, data.data.total))
            }

            override fun onError(error: APIError) {
                if (!concurrent) mGetEventListByProducerCall = null else mConcurrentCalls.remove(call)
                if (error.message == EXPIRED) return onTokenExpired()

                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mGetEventListByProducerCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) {
                    return when (error.localizedMessage) {
                        CANCELED_CALL, SOCKET_CLOSED -> if (onCanceledCall != null) onCanceledCall() else return
                        else -> onConnectionError(error)
                    }
                }

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<ResponseHits<EventJSON>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Event description
     *
     * @param eventId - id of the event to fetch
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onTokenExpired - token expired callback
     * @param onConnectionError - connection error callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "Event(client).getProducerEventDetails(request = ProducerEventDetails())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.ProducerEventDetails",
            ]
        )
    )
    fun getEventDescription(eventId: String,
                            onSuccess: (String) -> Unit,
                            onError: ErrorBlock,
                            onTokenExpired: Block,
                            onConnectionError: (error: Throwable) -> Unit) {

        val call  = service.getEventField(eventId, "description")

        val callback = object: IngresseCallback<DataJSON<EventJSON>?> {
            override fun onSuccess(data: DataJSON<EventJSON>?) {
                if (cancelAllCalled) return
                val response = data?.data?.description ?: return onError(APIError.default)
                onSuccess(response)
                mGetEventDescriptionCall = null
            }

            override fun onError(error: APIError) {
                mGetEventDescriptionCall = null
                if (error.message == "expired") return onTokenExpired()

                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                mGetEventDescriptionCall = null
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<DataJSON<EventJSON>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get event staff: list of admin and entrance manager for selected event
     *
     * @param eventId - id of the event to fetch
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onTokenExpired - token expired callback
     * @param onConnectionError - connection error callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "Event(client).getProducerEventDetails(request = ProducerEventDetails())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.ProducerEventDetails",
            ]
        )
    )
    fun getEventStaff(eventId: String,
                      onSuccess: (EventStaffJSON) -> Unit,
                      onError: ErrorBlock,
                      onTokenExpired: Block,
                      onConnectionError: Block
    ) {
        val call = service.getEventField(eventId, "staff")
        val callback = object: IngresseCallback<DataJSON<EventJSON>?> {
            override fun onSuccess(data: DataJSON<EventJSON>?) {
                val response = data?.data?.staff ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError()

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<DataJSON<EventJSON>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}