package com.ingresse.sdk

import com.ingresse.sdk.services.AuthService
import com.ingresse.sdk.services.EntranceService
import com.ingresse.sdk.services.SearchService

class IngresseService(client: IngresseClient) {
    var entrance: EntranceService = EntranceService(client)
    var auth: AuthService = AuthService(client)
    var search: SearchService = SearchService(client)
}