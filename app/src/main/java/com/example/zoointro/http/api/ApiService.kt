package com.example.zoointro.http.api

import com.example.zoointro.data.PlantInfoResponse
import com.example.zoointro.data.VenueInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * 取得場館資訊
     */
    @GET("v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire")
    suspend fun getVenueInfo(): VenueInfoResponse

    /**
     * 取得植物資訊
     * q : location
     * limit : per page
     * offset : page
     */
    @GET("v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire")
    suspend fun getPlantInfo(
        @Query("q") queryText: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PlantInfoResponse
}