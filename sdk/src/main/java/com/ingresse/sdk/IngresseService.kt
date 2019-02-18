package com.ingresse.sdk

import com.ingresse.sdk.services.*

class IngresseService(client: IngresseClient) {
    var auth: AuthService = AuthService(client)
    var checkin: CheckinService = CheckinService(client)
    var entrance: EntranceService = EntranceService(client)
    var permission: PermissionService = PermissionService(client)
    var search: SearchService = SearchService(client)
    var user: UserService = UserService(client)
    var refund: RefundService = RefundService(client)
}