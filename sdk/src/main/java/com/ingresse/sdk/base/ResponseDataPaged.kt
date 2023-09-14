package com.ingresse.sdk.base

class ResponseDataPaged<T> {
    var responseData: T? = null
    var responsePagination: ResponsePagination? = null

    class ResponsePagination {
        var page: Int = 0
        var lastPage: Int = 0
        var pageSIze: Int = 0
        var totalItems: Int = 0

        fun isLastPage(): Boolean = page >= lastPage
        fun nextPage(): Int = page + 1
        fun progress(): Double = if (lastPage == 0) 100.0 else page.toDouble()/lastPage
    }
}
