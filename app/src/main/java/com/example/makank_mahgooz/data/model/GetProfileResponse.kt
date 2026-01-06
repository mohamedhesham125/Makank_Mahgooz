package com.example.makank_mahgooz.data.model

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(
    val firstName: String,
    val lastName: String,
    val email: String,
    @SerializedName("token"           ) var token          : String?                   = null
)
