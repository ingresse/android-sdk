package com.ingresse.sdk

import com.ingresse.sdk.services.*

class IngresseService(client: IngresseClient) {
    var auth = AuthService(client)
    var checkin = CheckinService(client)
    var entrance = EntranceService(client)
    var permission = PermissionService(client)
    var search = SearchService(client)
    var ticketList = TicketListService(client)
    var ticketStatus = TicketStatusService(client)
    var transaction = TransactionService(client)
    var user = UserService(client)
}