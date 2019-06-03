package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.*
import com.ingresse.sdk.base.Array
import com.ingresse.sdk.builders.*
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.*
import com.ingresse.sdk.model.response.*
import com.ingresse.sdk.request.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class UserService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: User

    private var mUserDataCall: Call<String>? = null
    private var mUpdateBasicInfosCall: Call<String>? = null
    private var mUserTicketsCall: Call<String>? = null
    private var mUserTransfersCall: Call<String>? = null
    private var mRecentTransfersCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .baseUrl(URLBuilder(host, client.environment).build())
                .build()

        service = adapter.create(User::class.java)
    }

    /**
     * Method to cancel user data request
     */
    fun cancelUserData() = mUserDataCall?.cancel()

    /**
     * Method to cancel user update basic infos request
     */
    fun cancelUpdateBasicInfos() = mUpdateBasicInfosCall?.cancel()

    /**
     * Method to cancel user tickets data request
     */
    fun cancelUserTicketsData(concurrent: Boolean = false) {
        if(!concurrent) {
            mUserTicketsCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Method to cancel transfers data request
     */
    fun cancelUserTransfersData(concurrent: Boolean = false) {
        if(!concurrent) {
            mUserTransfersCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    fun cancelRecentTransfersData(concurrent: Boolean = false) {
        if(!concurrent) {
            mRecentTransfersCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Get user data
     *
     * @param request - all parameters used for retrieving user data
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getUserData(request: UserData, onSuccess: (UserDataJSON) -> Unit, onError: (APIError) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        val fields = listOf("id", "name", "lastname",
                "document", "email", "zip",
                "number", "complement", "city",
                "state", "street", "district",
                "phone", "verified", "fbUserId",
                "type", "pictures", "picture")

        val customFields = request.fields?.let { it } ?: fields.joinToString(",")
        mUserDataCall = service.getUserData(
                userId = request.userId,
                apikey = client.key,
                userToken = request.userToken,
                fields = customFields
        )

        val callback = object : IngresseCallback<Response<UserDataJSON>?> {
            override fun onSuccess(data: Response<UserDataJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<UserDataJSON>>() {}.type
        mUserDataCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Update user basic infos
     *
     * @param request - all parameters used for update user infos
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun updateBasicInfos(request: UserBasicInfos, onSuccess: (UserUpdatedDataJSON) -> Unit, onError: (APIError) -> Unit) {
        mUpdateBasicInfosCall = service.updateBasicInfos(
            userId = request.userId,
            apikey = client.key,
            userToken = request.userToken,
            params = request
        )

        val callback = object : IngresseCallback<Response<UserUpdatedJSON>?> {
            override fun onSuccess(data: Response<UserUpdatedJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if (!response.message.isNullOrEmpty()) {
                    val apiError = APIError()
                    apiError.message = response.message.joinToString(", ")
                    apiError.title = "Verifique suas informações"
                    apiError.code = 0
                    onError(apiError)
                    return
                }
                
                if (response.status == null) return onError(APIError.default)

                if (response.status) {
                    response.data?.let { obj -> onSuccess(obj) }
                    return
                }

                val responseData = response.data ?: return onError(APIError.default)
                onSuccess(responseData)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<UserUpdatedJSON>>() {}.type
        mUpdateBasicInfosCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get user tickets data
     *
     * @param concurrent - parameter to concurrent request
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getUserTicketsData(concurrent: Boolean = false,
                           request: UserTicketsData,
                           onSuccess: (Array<UserTicketsJSON>) -> Unit,
                           onError: (APIError) -> Unit,
                           onConnectionError: (error: Throwable) -> Unit) {

        val call = service.getUserTickets(
            userId = request.userId,
            apikey = client.key,
            page = request.page,
            pageSize = request.pageSize,
            token = request.userToken
        )

        if (!concurrent) mUserTicketsCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<Response<Array<UserTicketsJSON>>?> {
            override fun onSuccess(data: Response<Array<UserTicketsJSON>>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if(!concurrent) mUserTicketsCall = null else mConcurrentCalls.remove(call)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mUserTicketsCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mUserTicketsCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<Array<UserTicketsJSON>>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get user transfers data
     *
     * @param concurrent - parameters to concurrent request
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getUserTransfersData(concurrent: Boolean = false,
                             request: UserTransfersData,
                             onSuccess: (Array<UserTransfersJSON>) -> Unit,
                             onError: (APIError) -> Unit,
                             onConnectionError: (error: Throwable) -> Unit) {

        var call = service.getUserTransfers(
            userId = request.userId,
            apikey = client.key,
            page = request.page,
            pageSize = request.pageSize,
            token = request.usertoken,
            status = request.status
        )

        if (!concurrent) mUserTransfersCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<Response<Array<UserTransfersJSON>>?> {
            override fun onSuccess(data: Response<Array<UserTransfersJSON>>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if(!concurrent) mUserTransfersCall = null else mConcurrentCalls.remove(call)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mUserTransfersCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mUserTransfersCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<Array<UserTransfersJSON>>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Gets user recent transfers data
     *
     * @param concurrent - parameters to concurrent request
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getRecentTransfersData(concurrent: Boolean = false,
                               request: RecentTransfers,
                               onSuccess: (List<RecentTransfersJSON>) -> Unit,
                               onError: (APIError) -> Unit,
                               onConnectionError: (error: Throwable) -> Unit) {

        var call = service.getRecentTransfers(
            userId = request.userId,
            apikey = client.key,
            userToken = request.usertoken,
            order = request.order,
            size = request.size
        )

        if (!concurrent) mRecentTransfersCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<Response<List<RecentTransfersJSON>>?> {
            override fun onSuccess(data: Response<List<RecentTransfersJSON>>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if(!concurrent) mRecentTransfersCall = null else mConcurrentCalls.remove(call)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mRecentTransfersCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mRecentTransfersCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<List<RecentTransfersJSON>>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}