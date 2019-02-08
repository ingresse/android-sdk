package com.ingresse.sdk

import com.ingresse.sdk.services.AuthService
import com.ingresse.sdk.services.EntranceService
import com.ingresse.sdk.services.PermissionService
import com.ingresse.sdk.services.UserService

class IngresseService(client: IngresseClient) {
    var entrance: EntranceService = EntranceService(client)
    var auth: AuthService = AuthService(client)
    var user: UserService = UserService(client)
    var permission: PermissionService = PermissionService(client)
}