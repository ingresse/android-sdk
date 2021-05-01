package com.ingresse.sdk

import com.ingresse.sdk.v2.repositories.EventDetails
import com.ingresse.sdk.v2.repositories.Highlights
import com.ingresse.sdk.v2.repositories.Home
import com.ingresse.sdk.v2.repositories.Password
import com.ingresse.sdk.v2.repositories.PasswordStrength
import com.ingresse.sdk.v2.repositories.Search
import com.ingresse.sdk.v2.repositories.UserData
import com.ingresse.sdk.v2.repositories.UserWallet

interface V2Services {
    val eventDetails: EventDetails
    val highlights: Highlights
    val home: Home
    val password: Password
    val passwordStrength: PasswordStrength
    val search: Search
    val userData: UserData
    val userWallet: UserWallet
}
