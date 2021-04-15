package com.example.zoointro.repository

import com.example.zoointro.http.api.ApiService

class ApiRepository(private val apiService: ApiService) : BaseRepository() {
    suspend fun getVenueInfo() = safeApiCall {
        apiService.getVenueInfo()
    }

    suspend fun getPlantInfo(location: String, limit: Int, offset: Int) = safeApiCall {
        apiService.getPlantInfo(location, limit, offset)
    }
}

