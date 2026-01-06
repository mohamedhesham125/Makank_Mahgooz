package com.example.makank_mahgooz.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.makank_mahgooz.data.model.VehicleDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddVehiclesViewModel @Inject constructor() : ViewModel() {

    // This will be used to create a new vehicle and delegate adding it
    fun createVehicle(
        plateChar: String,
        plateNum: String,
        onAdd: (VehicleDetails) -> Unit
    ) {
        val vehicle = VehicleDetails(
            Id = UUID.randomUUID().toString(),
            plateChar = plateChar,
            PlateNum = plateNum
        )
        onAdd(vehicle)
    }
}
