package com.example.zoointro.repository

import android.accounts.NetworkErrorException
import com.example.zoointro.data.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
    protected suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResponseResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResponseResult.Success(apiCall.invoke())
            } catch (e: Exception) {
                when (e) {
                    is HttpException ->
                        ResponseResult.GenericError(e.code())
                    is NetworkErrorException -> {
                        ResponseResult.Failure(e)
                    }
                    else -> {
                        ResponseResult.Failure(e)
                    }
                }
            }
        }
    }
}