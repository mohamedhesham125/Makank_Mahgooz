package com.example.makank_mahgooz.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.makank_mahgooz.data.model.VehicleDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VehiclesViewModel @Inject constructor() : ViewModel() {

    val vehicles = mutableStateListOf<VehicleDetails>()

    fun addVehicle(vehicle: VehicleDetails) {
        vehicles.add(vehicle)
    }
}
