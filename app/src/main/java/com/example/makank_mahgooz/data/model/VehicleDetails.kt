package com.example.makank_mahgooz.data.model

import com.google.gson.annotations.SerializedName


data class VehicleDetails (

  @SerializedName("plate_Char" ) var plateChar : String,
  @SerializedName("Plate_Num"  ) var PlateNum  : String,
  @SerializedName("_id"        ) var Id        : String

)