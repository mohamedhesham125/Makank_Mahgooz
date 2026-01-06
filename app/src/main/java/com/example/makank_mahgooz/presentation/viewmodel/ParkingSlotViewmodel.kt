package com.example.makank_mahgooz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.makank_mahgooz.data.model.ParkingSlots
import com.example.makank_mahgooz.data.model.SlotStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ParkingSlotViewmodel @Inject constructor() : ViewModel() {
    private val _slots = MutableStateFlow<List<ParkingSlots>>(emptyList())
    val slots: StateFlow<List<ParkingSlots>> = _slots

    private val _selectedSlot = MutableStateFlow<ParkingSlots?>(null)
    val selectedSlot: StateFlow<ParkingSlots?> = _selectedSlot

    init {
        val initialSlots = List(20) { index ->
            ParkingSlots(id = "B0${index + 1}")
        }
        _slots.value = initialSlots
    }

    fun selectSlot(slotId: String) {
        // استخراج الرقم من الـ slotId مثلاً "B01" → 1
        val slotNumber = slotId.drop(1).toIntOrNull()

        if (slotNumber != null && slotNumber > 0) {
            _slots.update { currentList ->
                currentList.map { slot ->
                    if (slot.id == slotId) {
                        val selected = slot.copy(status = SlotStatus.SELECTED, selectedSlot = true)
                        _selectedSlot.value = selected
                        selected
                    } else {
                        slot.copy(status = SlotStatus.AVAILABLE, selectedSlot = false)
                    }
                }
            }
        }
    }

}
