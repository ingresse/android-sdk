package com.ingresse.sdk.model.response

data class TicketboothValidateJSON(
    var responseData : String = "",
    var status : String? = "",
    var responseDetails : String? = "",
    var responseStatus : Int? = 0,
    var responseError: ResponseError? = null)

data class ResponseError(
    var status: Boolean = false,
    var category: String = "",
    var code : String = "",
    var message : String )