package com.example.makank_mahgooz.data.model

data class ParkingSlots(
    val id: String,
    var status: SlotStatus = SlotStatus.AVAILABLE,
    var selectedSlot: Boolean = false
)
