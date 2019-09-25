package com.ingresse.sdk.model.response

data class PrintTicketsJSON(
    var data: List<TicketToPrint> = emptyList())

data class TicketToPrint(
    var eventAddress: String = "",
    var eventVenue: String = "",
    var eventTitleL1: String = "",
    var eventTitleL2: String = "",
    var eventCNPJorCPF: String = "",
    var eventCityNumber: String = "",
    var eventFormalName: String = "",
    var openField1: String = "",
    var openField2: String = "",
    var saleOperatorName: String = "",
    var saleDate: String = "",
    var purchaseDate: String = "",
    var salePayment: String = "",
    var eventType: String = "",
    var eventDate: String = "",
    var eventDay: String = "",
    var eventMonth: String = "",
    var eventTimeHH: String = "",
    var eventTimeMM: String = "",
    var ticketTypeName: String = "",
    var ticketGuestType: String = "",
    var ticketPrice: String = "",
    var ticketIngresseTax: String = "",
    var ticketDescription: String = "",
    var ticketCode: String = "",
    var ticketSequence: String = "")