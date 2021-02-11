package com.ingresse.sdk.v2.models.response.eventAttributes

data class AdvertisementJSON(
    val mobile: MobileAdvertisementJSON?,
) {
    data class MobileAdvertisementJSON(
        val background: BackgroundJSON?,
        val cover: CoverJSON?,
    ) {
        data class BackgroundJSON(
            val image: String?,
        )

        data class CoverJSON(
            val image: String?,
            val url: String?,
        )
    }
}
