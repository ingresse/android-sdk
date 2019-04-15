package com.ingresse.sdk.model.response

data class PlannerAttributesJSON(
    var title: String = "",
    var posImage: String?,
    var planner: PlannerData?,
    var aiddp: String?,
    var formalName: String?,
    var cnpj: String?,
    var cpf: String?,
    var obs2: String?,
    var cityNumber: String?,
    var address: String?
)

data class PlannerData(
    var name: String = "",
    var logo: String = "",
    var link: String = "",
    var email: String = "",
    var phone: String = ""
)