package com.ingresse.sdk.model.response

data class CheckinHistoryJSON(
    var data: Array<CheckinHistoryDataJSON>
)

data class CheckinHistoryDataJSON(
    var session: SessionHistoryData,
    var owner: UserHistoryDataJSON,
    var lastStatus: CheckinLastStatusData
)

data class SessionHistoryData(
    var id: String,
    var dateTime: DatetimeHistoryData
)

data class DatetimeHistoryData(
    var date: String,
    var time: String,
    var dateTime: String
)

data class CheckinLastStatusData(
    var id: String,
    var operation: String,
    var timestamp: Long,
    var operator: UserHistoryDataJSON,
    var holder: UserHistoryDataJSON
)