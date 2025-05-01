package com.flopez.core.domain.result

import com.flopez.core.domain.exception.MortyException

sealed interface Result<out Data, out Exception: MortyException> {
    data class Success<out Data, out Exception: MortyException>(val data: Data) : Result<Data, Exception>
    data class Error<out Data, out Exception: MortyException>(val error: Exception) : Result<Data, Exception>
}

fun <Data, Exception : MortyException> Result<Data, Exception>.onSuccess(action: (value: Data) -> Unit): Result<Data, Exception> {
    if (this is Result.Success) action(this.data)
    return this
}

fun <Data, Exception : MortyException> Result<Data, Exception>.onFailure(action: (value: Exception) -> Unit): Result<Data, Exception> {
    if (this is Result.Error) action(this.error)
    return this
}