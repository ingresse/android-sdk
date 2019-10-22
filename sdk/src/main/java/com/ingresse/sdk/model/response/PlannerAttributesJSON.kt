package com.ingresse.sdk.model.response

data class PlannerAttributesJSON(
    var title: String = "",
    var posImage: String?,
    var planner: PlannerData?,
    var aiddp: String?,
    var formalName: String?,
    var cityNumber: String?,
    var address: String?)

data class PlannerData(
    var name: String = "",
    var logo: String = "",
    var link: String = "",
    var email: String = "",
    var ddi: String? = "",
    var phone: String = "")