package com.ingresse.sdk

import com.ingresse.sdk.services.*

class IngresseService(client: IngresseClient) {
    var attributes = AttributesService(client)
    var auth = AuthService(client)
    var balance = BalanceService(client)
    var checkin = CheckinService(client)
    var entrance = EntranceService(client)
    var permission = PermissionService(client)
    var phone = PhoneService(client)
    var pos = POSService(client)
    var search = SearchService(client)
    var ticketList = TicketListService(client)
    var ticketStatus = TicketStatusService(client)
    var transaction = TransactionService(client)
    var transfer = TransferService(client)
    var user = UserService(client)
    var event = EventService(client)
    var ticket = TicketService(client)
    var report = ReportService(client)
    var history = HistoryService(client)
    var highlight = HighlightsService(client)
    var zipCode = ZipCodeService(client)
    var webSocketService = WebSocketService(client)
}