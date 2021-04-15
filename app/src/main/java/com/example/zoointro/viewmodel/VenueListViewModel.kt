package com.example.zoointro.viewmodel

import androidx.lifecycle.*
import com.example.zoointro.data.ResponseResult
import com.example.zoointro.repository.ApiRepository
import kotlinx.coroutines.Dispatchers

class VenueListViewModel(
    private val apiRepository: ApiRepository
) : ViewModel() {
    fun getVenueInfo() = liveResponse {apiRepository.getVenueInfo()}
}

inline fun <T> liveResponse(crossinline body: suspend () -> ResponseResult<T>) =
    liveData(Dispatchers.IO) {
        emit(ResponseResult.Loading)
        val result = body()
        emit(result)
        emit(ResponseResult.Complete)
    }