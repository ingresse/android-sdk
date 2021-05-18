package com.ingresse.sdk

import com.ingresse.sdk.v2.repositories.Auth
import com.ingresse.sdk.v2.repositories.CheckinReportThreshold
import com.ingresse.sdk.v2.repositories.Event
import com.ingresse.sdk.v2.repositories.EventDetails
import com.ingresse.sdk.v2.repositories.Highlights
import com.ingresse.sdk.v2.repositories.Home
import com.ingresse.sdk.v2.repositories.Password
import com.ingresse.sdk.v2.repositories.PasswordStrength
import com.ingresse.sdk.v2.repositories.Search
import com.ingresse.sdk.v2.repositories.Ticket
import com.ingresse.sdk.v2.repositories.UserData
import com.ingresse.sdk.v2.repositories.UserWallet

interface V2Services {
    val auth: Auth
    val event: Event
    val eventDetails: EventDetails
    val checkinReportThreshold: CheckinReportThreshold
    val highlights: Highlights
    val home: Home
    val password: Password
    val passwordStrength: PasswordStrength
    val search: Search
    val ticket: Ticket
    val userData: UserData
    val userWallet: UserWallet
}
