package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.Array
import com.ingresse.sdk.base.DataJSON
import com.ingresse.sdk.base.Ignored
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.CANCELED_CALL
import com.ingresse.sdk.helper.ErrorBlock
import com.ingresse.sdk.helper.SOCKET_CLOSED
import com.ingresse.sdk.model.request.CreateAccount
import com.ingresse.sdk.model.request.EventAttributes
import com.ingresse.sdk.model.request.UserAddressInfos
import com.ingresse.sdk.model.request.UserBasicInfos
import com.ingresse.sdk.model.request.UserChangePassword
import com.ingresse.sdk.model.request.UserData
import com.ingresse.sdk.model.request.UserPicture
import com.ingresse.sdk.model.request.UserPlanner
import com.ingresse.sdk.model.request.UserPlannerInfos
import com.ingresse.sdk.model.request.UserTicketsData
import com.ingresse.sdk.model.request.WalletEvents
import com.ingresse.sdk.model.response.CreateAccountJSON
import com.ingresse.sdk.model.response.EventAttributesJSON
import com.ingresse.sdk.model.response.StrengthPasswordJSON
import com.ingresse.sdk.model.response.UserDataJSON
import com.ingresse.sdk.model.response.UserTicketsJSON
import com.ingresse.sdk.model.response.UserUpdatedDataJSON
import com.ingresse.sdk.model.response.UserUpdatedJSON
import com.ingresse.sdk.model.response.WalletEventJSON
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
    private var mUpdateUserPictureCall: Call<String>? = null
    private var mUpdateUserAddressCall: Call<String>? = null
    private var mUpdateUserPlannerInfosCall: Call<String>? = null
    private var mUserTicketsCall: Call<String>? = null
    private var mGetEventAttributesCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()
    private var mGetWalletEventsCall: Call<String>? = null
    private var mGetWalletEventsConcurrentCalls: ArrayList<Call<String>> = ArrayList()
    private var mValidatePasswordStrengthCall: Call<String>? = null
    private var mChangePasswordCall: Call<String>? = null
    private var mCreateAccountCall: Call<String>? = null

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
     * Method to cancel user update picture request
     */
    fun cancelUpdateUserPicture() = mUpdateUserPictureCall?.cancel()

    /**
     * Method to cancel user update address infos request
     */
    fun cancelUpdateUserAddress() = mUpdateUserAddressCall?.cancel()

    /**
     * Method to cancel user update planner infos request
     */
    fun cancelUpdateUserPlannerInfos() = mUpdateUserPlannerInfosCall?.cancel()

    /**
     * Method to cancel user validated password strength
     */
    fun cancelValidatePasswordStrength() = mValidatePasswordStrengthCall?.cancel()

    /**
     * Method to cancel user validated password strength
     */
    fun cancelChangePassword() = mChangePasswordCall?.cancel()

    /**
     * Method to cancel a account creation request
     */
    fun cancelCreateAccount() = mCreateAccountCall?.cancel()

    /**
     * Method to cancel user tickets data request
     */
    fun cancelUserTicketsData(concurrent: Boolean = false) {
        if (!concurrent) {
            mUserTicketsCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Method to cancel a get event attributes
     */
    fun cancelGetEventAttributes() = mGetEventAttributesCall?.cancel()

    /**
     * Method to cancel a get wallet event list by user
     */
    fun cancelGetWalletEvents(concurrent: Boolean = false) {
        if (!concurrent) {
            mGetWalletEventsCall?.cancel()
            return
        }

        mGetWalletEventsConcurrentCalls.forEach { it.cancel() }
        mGetWalletEventsConcurrentCalls.clear()
    }

    /**
     * Get user data
     *
     * @param request - all parameters used for retrieving user data
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - user token expired callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "UserData(client).getUserData(request = Login())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.UserData",
            ]
        )
    )
    fun getUserData(
        request: UserData,
        onSuccess: (UserDataJSON) -> Unit,
        onError: ErrorBlock,
        onConnectionError: (Throwable) -> Unit,
        onTokenExpired: Block
    ) {

        val fields = listOf(
            "id", "name", "lastname",
            "document", "email", "zip", "number",
            "complement", "city", "state", "street",
            "district", "ddi", "phone", "verified", "fbUserId",
            "type", "pictures", "picture", "planner", "birthdate"
        )

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
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
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
     * @param onTokenExpired - user token expired callback
     */
    fun updateBasicInfos(
        request: UserBasicInfos,
        onSuccess: (UserUpdatedDataJSON) -> Unit,
        onError: ErrorBlock,
        onNetworkError: Block,
        onTokenExpired: Block
    ) {
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
                val responseData = response.data ?: return onError(APIError.default)
                if (!response.status) return onError(APIError.default)
                onSuccess(responseData)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) = onNetworkError()

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<UserUpdatedJSON>>() {}.type
        mUpdateBasicInfosCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Update user picture
     *
     * @param request - all parameters used for update user picture
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onTokenExpired - user token expired callback
     */
    fun updateUserPicture(
        request: UserPicture,
        onSuccess: (UserUpdatedDataJSON) -> Unit,
        onError: ErrorBlock,
        onNetworkError: Block,
        onTokenExpired: Block
    ) {

        request.picture = "data:image/png;base64,${request.picture}"
        mUpdateUserPictureCall = service.updateUserPicture(
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
                val responseData = response.data ?: return onError(APIError.default)
                if (!response.status) return onError(APIError.default)
                onSuccess(responseData)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) = onNetworkError()

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<UserUpdatedJSON>>() {}.type
        mUpdateUserPictureCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Update user address
     *
     * @param request - all parameters used for update address
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - token expired error callback
     */
    fun updateUserAddress(
        request: UserAddressInfos,
        onSuccess: (UserUpdatedDataJSON) -> Unit,
        onError: ErrorBlock,
        onConnectionError: (Throwable) -> Unit,
        onTokenExpired: Block
    ) {

        mUpdateUserAddressCall = service.updateUserAddress(
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

                val responseData = response.data ?: return onError(APIError.default)
                if (!response.status) return onError(APIError.default)

                onSuccess(responseData)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<UserUpdatedJSON>?>() {}.type
        mUpdateUserAddressCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Update user planner infos
     *
     * @param request - all parameters used for update user planner infos
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - user token expired callback
     */
    fun updateUserPlannerInfos(
        request: UserPlannerInfos,
        onSuccess: (UserUpdatedDataJSON) -> Unit,
        onError: ErrorBlock,
        onConnectionError: (error: Throwable) -> Unit,
        onTokenExpired: Block
    ) {
        val plannerRequest = UserPlanner(request)
        mUpdateUserPlannerInfosCall = service.updateUserPlannerInfos(
            userId = request.userId,
            userToken = request.userToken,
            apikey = client.key,
            params = plannerRequest
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

                if (response.status == null || !response.status) return onError(APIError.default)

                val responseData = response.data ?: return onError(APIError.default)
                onSuccess(responseData)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<UserUpdatedJSON>?>() {}.type
        mUpdateUserPlannerInfosCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get user tickets data
     *
     * @param concurrent - flag to concurrent request
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - user token expired callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "UserWallet(client).getUserTickets(request = UserTickets())",
            imports = ["com.ingresse.sdk.v2.repositories"]
        )
    )
    fun getUserTicketsData(
        concurrent: Boolean = false,
        request: UserTicketsData,
        onSuccess: (Array<UserTicketsJSON>) -> Unit,
        onError: (APIError) -> Unit,
        onConnectionError: (error: Throwable) -> Unit,
        onTokenExpired: Block,
        onCanceledCall: (() -> Unit)? = null
    ) {

        val call = service.getUserTickets(
            userId = request.userId,
            apikey = client.key,
            page = request.page,
            pageSize = request.pageSize,
            eventId = request.eventId,
            token = request.userToken
        )

        if (!concurrent) mUserTicketsCall = call else mConcurrentCalls.add(call)

        val callback = object : IngresseCallback<Response<Array<UserTicketsJSON>>?> {
            override fun onSuccess(data: Response<Array<UserTicketsJSON>>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if (!concurrent) mUserTicketsCall = null else mConcurrentCalls.remove(call)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mUserTicketsCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mUserTicketsCall = null else mConcurrentCalls.remove(call)
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

        val type = object : TypeToken<Response<Array<UserTicketsJSON>>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Event attributes
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - user token expired callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "EventDetails(client).getEventAttributes(request = EventAttributes())",
            imports = ["com.ingresse.sdk.v2.repositories"]
        )
    )
    fun getEventAttributes(
        request: EventAttributes,
        onSuccess: (EventAttributesJSON) -> Unit,
        onError: ErrorBlock,
        onConnectionError: (error: Throwable) -> Unit,
        onTokenExpired: Block
    ) {

        val call = service.getEventAttributes(
            eventId = request.eventId,
            apikey = client.key,
            userToken = request.userToken,
            filters = request.filters?.joinToString(",")
        )

        val callback = object : IngresseCallback<Response<EventAttributesJSON>?> {
            override fun onSuccess(data: Response<EventAttributesJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }
        val type = object : TypeToken<Response<EventAttributesJSON>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Wallet Event list by user
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - user token expired callback
     */
    fun getWalletEvents(
        concurrent: Boolean = false,
        request: WalletEvents,
        onSuccess: (Array<WalletEventJSON>) -> Unit,
        onError: (APIError) -> Unit,
        onConnectionError: (error: Throwable) -> Unit,
        onTokenExpired: Block,
        onCanceledCall: (() -> Unit)? = null
    ) {

        val call = service.getWalletEvents(
            apikey = client.key,
            userId = request.userId,
            token = request.userToken,
            page = request.page,
            pageSize = request.pageSize,
            dateFrom = request.from,
            dateTo = request.to
        )

        if (!concurrent) mGetWalletEventsCall = call else mGetWalletEventsConcurrentCalls.add(call)

        val callback = object : IngresseCallback<Response<Array<WalletEventJSON>>?> {
            override fun onSuccess(data: Response<Array<WalletEventJSON>>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if (!concurrent) mGetWalletEventsCall =
                    null else mGetWalletEventsConcurrentCalls.remove(call)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mGetWalletEventsCall =
                    null else mGetWalletEventsConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mGetWalletEventsCall =
                    null else mGetWalletEventsConcurrentCalls.remove(call)
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
        val type = object : TypeToken<Response<Array<WalletEventJSON>>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Validate password strength
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - user token expired callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "PasswordStrength(client).validateStrength(request = PasswordStrength())",
            imports = [
                "com.ingresse.sdk.v2.repositories",
                "com.ingresse.sdk.v2.models.request.PasswordStrength",
            ]
        )
    )
    fun validatePasswordStrength(
        request: String,
        onSuccess: (StrengthPasswordJSON) -> Unit,
        onError: ErrorBlock,
        onConnectionError: (Throwable) -> Unit,
        onTokenExpired: Block,
        onCanceledCall: (() -> Unit)? = null
    ) {

        mValidatePasswordStrengthCall = service.validatePasswordStrength(
            password = request,
            apikey = client.key
        )

        val callback = object : IngresseCallback<Response<StrengthPasswordJSON>?> {
            override fun onSuccess(data: Response<StrengthPasswordJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
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

        val type = object : TypeToken<Response<StrengthPasswordJSON?>>() {}.type
        mValidatePasswordStrengthCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Save new password
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - user token expired callback
     */
    fun changePassword(
        request: UserChangePassword,
        onSuccess: Block,
        onError: ErrorBlock,
        onConnectionError: (Throwable) -> Unit,
        onTokenExpired: Block
    ) {

        mChangePasswordCall = service.changePassword(
            userId = request.userId,
            usertoken = request.userToken,
            apikey = client.key,
            params = request.password
        )

        val callback = object : IngresseCallback<Ignored> {
            override fun onSuccess(data: Ignored?) = onSuccess()
            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Ignored>() {}.type
        mChangePasswordCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Create Ingresse account
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     * @param onTokenExpired - user token expired callback
     */
    fun createAccount(
        request: CreateAccount,
        onSuccess: (CreateAccountJSON) -> Unit,
        onError: ErrorBlock,
        onConnectionError: (Throwable) -> Unit,
        onTokenExpired: Block
    ) {
        mCreateAccountCall = service.createAccount(
            name = request.name,
            lastName = request.lastName,
            ddi = request.ddi,
            phone = request.phone,
            document = request.document,
            birthdate = request.birthdate,
            email = request.email,
            password = request.password,
            newsletter = request.newsletter,
            apikey = client.key
        )

        val callback = object : IngresseCallback<Response<DataJSON<CreateAccountJSON>>?> {
            override fun onSuccess(data: Response<DataJSON<CreateAccountJSON>>?) {
                val response = data?.responseData?.data ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<DataJSON<CreateAccountJSON>>?>() {}.type
        mCreateAccountCall?.enqueue(RetrofitCallback(type, callback))
    }
}
