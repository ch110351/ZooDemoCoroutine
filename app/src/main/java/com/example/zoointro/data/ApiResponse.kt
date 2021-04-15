package com.example.zoointro.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * api response
 */

/**
 * 場館資訊
 */

data class VenueInfoResponse(
    val result: VenueResult
)

data class VenueResult(
    @SerializedName("count")
    val count: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: List<VenueEntity>,
    @SerializedName("sort")
    val sort: String
)

@Parcelize
data class VenueEntity(
    @SerializedName("E_Category")
    val category: String,
    @SerializedName("E_Geo")
    val geo: String,
    @SerializedName("E_Info")
    val info: String,
    @SerializedName("E_Memo")
    val memo: String,
    @SerializedName("E_Name")
    var name: String,
    @SerializedName("E_Pic_URL")
    val picUrl: String,
    @SerializedName("E_URL")
    val url: String,
    @SerializedName("E_no")
    val no: String,
    @SerializedName("_id")
    val id: Int
) : Parcelable

/**
 * 植物資訊
 */

data class PlantInfoResponse(
    val result: PlantResult
)

data class PlantResult(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<PlantEntity>,
    val sort: String
)

@Parcelize
data class PlantEntity(
    @SerializedName("F_AlsoKnown")
    val alsoKnown: String?,
    @SerializedName("F_Brief")
    val brief: String?,
    @SerializedName("F_Feature")
    val feature: String?,
    @SerializedName("F_Function＆Application")
    val functionAndApplication: String?,
    @SerializedName("F_Location")
    val location: String?,
    @SerializedName("F_Name_En")
    val nameEn: String?,
    @SerializedName("F_Pic01_URL")
    val picUrl: String?,
    @SerializedName("F_Update")
    val update: String?,
    val id: Int?,
    @SerializedName("﻿F_Name_Ch")
    val nameCh: String?
): Parcelable