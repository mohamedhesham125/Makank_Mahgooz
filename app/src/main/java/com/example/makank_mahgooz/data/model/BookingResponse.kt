package com.example.makank_mahgooz.data.model

data class BookingResponse(
    val user: Customer_inform,
    val spotId: Int,
    val plate_Char: String,
    val Plate_Num: String,
    val startTime: String,
    val endTime: String,
    val duration: String,
    val status: String,
    val _id: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
    val token: String
)