package com.ingresse.sdk

import com.ingresse.sdk.services.AuthService
import com.ingresse.sdk.services.CheckinService
import com.ingresse.sdk.services.EntranceService
import com.ingresse.sdk.services.PermissionService
import com.ingresse.sdk.services.SearchService
import com.ingresse.sdk.services.UserService

class IngresseService(client: IngresseClient) {
    var auth: AuthService = AuthService(client)
    var checkin: CheckinService = CheckinService(client)
    var entrance: EntranceService = EntranceService(client)
    var permission: PermissionService = PermissionService(client)
    var search: SearchService = SearchService(client)
    var user: UserService = UserService(client)
}