package com.ingresse.sdk

import com.ingresse.sdk.services.EntranceService

class IngresseService(client: IngresseClient) {
    var entrance: EntranceService = EntranceService(client)
}