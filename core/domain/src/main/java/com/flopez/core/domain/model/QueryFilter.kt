package com.flopez.core.domain.model

//data class QueryFilter(
//    val name: String
//) {
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (other !is QueryFilter) return false
//
//        if (name != other.name) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        return name.hashCode()
//    }
//}

sealed class QueryFilter {
    data class ByName(val name: String) : QueryFilter()
}