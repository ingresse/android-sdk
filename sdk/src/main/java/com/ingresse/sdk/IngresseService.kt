package com.ingresse.sdk

import com.ingresse.sdk.services.CheckinService
import com.ingresse.sdk.services.EntranceService

class IngresseService(client: IngresseClient) {
    var checkin: CheckinService = CheckinService(client)
    var entrance: EntranceService = EntranceService(client)
}