package com.ingresse.sdk.v2.models.response

data class HomeJSON(
    val categories: CategoriesJSON,
) {

    data class CategoriesJSON(
        val visible: List<Items>,
        val hidden: List<Items>
    ) {

        data class Items(
            val id: Int,
            val category: String,
            val slug: String,
            val totalItems: Int,
        )
    }
}
