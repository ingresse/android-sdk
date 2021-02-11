package com.ingresse.sdk

import com.ingresse.sdk.v2.repositories.EventDetails
import com.ingresse.sdk.v2.repositories.Highlights
import com.ingresse.sdk.v2.repositories.Search
import com.ingresse.sdk.v2.repositories.UserWallet

interface V2Services {
    val highlights: Highlights
    val search: Search
    val eventDetails: EventDetails
    val userWallet: UserWallet
}