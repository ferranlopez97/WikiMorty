package com.flopez.core.domain.pagination

import com.flopez.core.domain.model.QueryFilter


data class PagedDataState (
    val pageSize : Int,
    var currentPage: Int,
    var hasNextPage: Boolean,
    var currentFilter: QueryFilter,
) {
    companion object {
        fun  empty(
            pageSize: Int
        ) : PagedDataState {
            return PagedDataState(
                pageSize = pageSize,
                currentPage = 1,
                hasNextPage = true,
                currentFilter = QueryFilter.ByName("")
            )
        }
    }
}
