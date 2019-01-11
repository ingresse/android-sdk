package com.ingresse.sdk.base

class PaginationInfo {
    var currentPage: Int = 0
    var lastPage: Int = 0
    var totalResults: Int = 0

    val isLastPage: Boolean = currentPage >= lastPage
    val nextPage: Int = currentPage + 1
    val progress: Double = if (lastPage == 0) 100.0 else currentPage.toDouble()/lastPage
}
