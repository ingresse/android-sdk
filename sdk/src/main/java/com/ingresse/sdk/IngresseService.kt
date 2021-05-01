package com.ingresse.sdk

import com.ingresse.sdk.services.AttributesService
import com.ingresse.sdk.services.AuthService
import com.ingresse.sdk.services.BalanceService
import com.ingresse.sdk.services.CheckinService
import com.ingresse.sdk.services.EntranceReportService
import com.ingresse.sdk.services.EntranceService
import com.ingresse.sdk.services.EventDetailsService
import com.ingresse.sdk.services.EventLiveService
import com.ingresse.sdk.services.EventService
import com.ingresse.sdk.services.HighlightService
import com.ingresse.sdk.services.HistoryService
import com.ingresse.sdk.services.POSService
import com.ingresse.sdk.services.PermissionService
import com.ingresse.sdk.services.PhoneService
import com.ingresse.sdk.services.ReportService
import com.ingresse.sdk.services.SearchService
import com.ingresse.sdk.services.TicketListService
import com.ingresse.sdk.services.TicketService
import com.ingresse.sdk.services.TicketStatusService
import com.ingresse.sdk.services.TransactionService
import com.ingresse.sdk.services.TransferService
import com.ingresse.sdk.services.UserService
import com.ingresse.sdk.services.WebSocketService
import com.ingresse.sdk.services.ZipCodeService
import com.ingresse.sdk.v2.repositories.Auth
import com.ingresse.sdk.v2.repositories.EventDetails
import com.ingresse.sdk.v2.repositories.Highlights
import com.ingresse.sdk.v2.repositories.Home
import com.ingresse.sdk.v2.repositories.Password
import com.ingresse.sdk.v2.repositories.PasswordStrength
import com.ingresse.sdk.v2.repositories.Search
import com.ingresse.sdk.v2.repositories.UserData
import com.ingresse.sdk.v2.repositories.UserWallet

class IngresseService(var client: IngresseClient) {
    var attributes = AttributesService(client)
    var auth = AuthService(client)
    var balance = BalanceService(client)
    var checkin = CheckinService(client)
    var entrance = EntranceService(client)
    var entranceReport = EntranceReportService(client)
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
    var eventDetails = EventDetailsService(client)
    var ticket = TicketService(client)
    var report = ReportService(client)
    var history = HistoryService(client)
    var highlight = HighlightService(client)
    var zipCode = ZipCodeService(client)
    var webSocketService = WebSocketService(client)
    var live = EventLiveService(client)

    var v2 = object : V2Services {
        override val authService = Auth(client)
        override val eventDetails = EventDetails(client)
        override val highlights = Highlights(client)
        override val home = Home(client)
        override val password = Password(client)
        override val passwordStrength = PasswordStrength(client)
        override val search = Search(client)
        override val userData = UserData(client)
        override val userWallet = UserWallet(client)
    }
}
