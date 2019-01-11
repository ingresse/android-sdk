package com.ingresse.androidsdk

import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.IngresseService

const val PUBLIC_KEY = "172f24fd2a903fc0647b61d7112ee1b9814702be"
const val API_HOST = "https://api.ingresse.com/"

object ServiceManager {
    var service: IngresseService

    init {
        val client = IngresseClient(PUBLIC_KEY, API_HOST)
        service = IngresseService(client)
    }
}
