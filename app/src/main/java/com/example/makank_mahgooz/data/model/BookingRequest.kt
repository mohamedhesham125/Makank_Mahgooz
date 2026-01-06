package com.example.makank_mahgooz.data.model

import com.google.gson.annotations.SerializedName

data class BookingRequest(
    @SerializedName("Plate_Num") val Plate_Num: String,
    @SerializedName("plate_Char") val plate_Char: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("spotId") val spotId: Int
)