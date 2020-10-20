package com.ingresse.sdk.v2.models.base

data class PagedResponse<T>(
    var responseData: ResponseData<T>? = null
)

data class ResponseData<T>(
    val paginationInfo: PaginationInfo?,
    var data: ArrayList<T> = ArrayList()
)

data class PaginationInfo(
    var currentPage: Int,
    var lastPage: Int,
    var totalResults: Int
) {
    fun isLastPage(): Boolean = currentPage >= lastPage
    fun nextPage(): Int = currentPage + 1
}
