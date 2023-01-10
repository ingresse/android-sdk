package com.ingresse.sdk.v2.models.request.base

enum class UserGender(val value: String) {
    WOMAN_CISGENDER("FC"),
    WOMAN_TRANSGENDER("FT"),
    MAN_CISGENDER("MC"),
    MAN_TRANSGENDER("MT"),
    NON_BINARY("NB"),
    OTHER("O")
}
