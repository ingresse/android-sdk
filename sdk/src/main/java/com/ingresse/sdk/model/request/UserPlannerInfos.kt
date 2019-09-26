package com.ingresse.sdk.model.request

import com.google.gson.annotations.SerializedName

data class UserPlanner(var planner: UserPlannerInfos)

data class UserPlannerInfos(
        var userId: String,
        var userToken: String,
        @SerializedName("name")
        var tradeName: String? = null,
        @SerializedName("socialName")
        var corporateName: String? = null,
        @SerializedName("cityNumber")
        var municipalRegistration: String? = null,
        var email: String? = null,
        var link: String? = null,
        var cpf: String? = null,
        var cnpj: String? = null,
        var document: String? = null,
        var phone: String? = null,
        var picture: String? = null,
        @SerializedName("openField1")
        var obs: String? = null)