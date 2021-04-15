package com.example.zoointro.data

import com.example.zoointro.R

sealed class ResponseResult<out T> {
    data class Success<out T>(val data: T) : ResponseResult<T>()

    data class Failure(val error: Throwable) : ResponseResult<Nothing>() {
        var catchException: String? = error.message
    }

    data class GenericError(val code: Int? = null) : ResponseResult<Nothing>() {
        var catchException: Int =
            when (code) {
                in 500 until 600 -> R.string.common_error_server
                in 400 until 500 -> R.string.common_error_request
                else -> R.string.common_error_request
            }
    }

    object Loading : ResponseResult<Nothing>()
    object Complete : ResponseResult<Nothing>()
}


