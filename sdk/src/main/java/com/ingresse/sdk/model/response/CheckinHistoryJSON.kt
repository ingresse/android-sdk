package com.ingresse.sdk.model.response

data class CheckinHistoryJSON(
        var session: SessionHistoryJSON,
        var owner: UserHistoryDataJSON,
        var lastStatus: CheckinLastStatusJSON?)

data class SessionHistoryJSON(
        var id: String,
        var dateTime: DatetimeHistoryJSON)

data class DatetimeHistoryJSON(
        var date: String,
        var time: String,
        var dateTime: String)

data class CheckinLastStatusJSON(
        var id: String,
        var operation: String,
        var timestamp: Long,
        var operator: UserHistoryDataJSON,
        var holder: UserHistoryDataJSON)