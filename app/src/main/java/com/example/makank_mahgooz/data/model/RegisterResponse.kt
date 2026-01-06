package com.example.makank_mahgooz.data.model

import com.google.gson.annotations.SerializedName


data class RegisterResponse (
  @SerializedName("_id"             ) var Id             : String?                   = null,
  @SerializedName("name"            ) var name           : String?                   = null,
  @SerializedName("email"           ) var email          : String?                   = null,
  @SerializedName("password"        ) var password       : String?                   = null,
  @SerializedName("phone"           ) var phone          : String?                   = null,
  @SerializedName("__v"             ) var _v             : Int?                      = null,
  @SerializedName("createdAt"       ) var createdAt      : String?                   = null,
  @SerializedName("updatedAt"       ) var updatedAt      : String?                   = null,
  @SerializedName("token"           ) var token          : String?                   = null
)