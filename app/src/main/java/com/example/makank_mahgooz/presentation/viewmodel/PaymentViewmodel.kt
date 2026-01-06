package com.example.makank_mahgooz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makank_mahgooz.data.model.BookingRequest
import com.example.makank_mahgooz.data.model.BookingResponse
import com.example.makank_mahgooz.data.model.ParkingSlots
import com.example.makank_mahgooz.data.model.SlotStatus
import com.example.makank_mahgooz.data.model.VehicleDetails
import com.example.makank_mahgooz.data.networking.ApiServices
import com.example.makank_mahgooz.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PaymentViewmodel @Inject constructor(
    private val apiService: ApiServices, private val authInterceptor: AuthInterceptor
) : ViewModel() {
    private val _slots = MutableStateFlow<List<ParkingSlots>>(emptyList())
    val slots: StateFlow<List<ParkingSlots>> = _slots

    private val _selectedSlot = MutableStateFlow<ParkingSlots?>(null)
    val selectedSlot: StateFlow<ParkingSlots?> = _selectedSlot

    private val _vehicle = MutableStateFlow<VehicleDetails?>(null)
    val vehicle: StateFlow<VehicleDetails?> = _vehicle

    private val _bookingConfirmed = MutableStateFlow(false)
    val bookingConfirmed: StateFlow<Boolean> = _bookingConfirmed

    private val _startTime = MutableStateFlow("Not selected")
    val startTime: StateFlow<String> = _startTime

    private val _endTime = MutableStateFlow("Not selected")
    val endTime: StateFlow<String> = _endTime

    private val _duration = MutableStateFlow("0 min")
    val duration: StateFlow<String> = _duration

    private val _bookingResponse = MutableStateFlow<BookingResponse?>(null)
    val bookingResponse: StateFlow<BookingResponse?> = _bookingResponse

    init {
        val initialSlots = List(20) { index ->
            ParkingSlots(id = "B0${index + 1}")
        }
        _slots.value = initialSlots
    }

    fun selectSlot(slotId: String) {
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

    fun setVehicle(vehicleDetails: VehicleDetails) {
        _vehicle.value = vehicleDetails
    }

    fun confirmBooking() {
        _bookingConfirmed.value = true
    }

    fun setStartTime(value: String) {
        _startTime.value = value
    }

    fun setEndTime(value: String) {
        _endTime.value = value
    }

    fun setDuration(value: String) {
        _duration.value = value
    }

    fun formatToISO(time: String): String {
        val inputFormat = SimpleDateFormat("hh:mm a", Locale.US)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        val date = inputFormat.parse(time) ?: throw IllegalArgumentException("Invalid time format")
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        val parts = currentDate.split("-")

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, parts[0].toInt())
            set(Calendar.MONTH, parts[1].toInt() - 1)
            set(Calendar.DAY_OF_MONTH, parts[2].toInt())
            set(Calendar.HOUR_OF_DAY, date.hours)
            set(Calendar.MINUTE, date.minutes)
            set(Calendar.SECOND, 0)
        }

        return outputFormat.format(calendar.time)
    }



    fun sendBookingRequest() {
        val vehicleData = vehicle.value ?: return
        val selected = selectedSlot.value ?: return

        val formattedStartTime = formatToISO(_startTime.value)
        val formattedEndTime = formatToISO(_endTime.value)

        val bookingRequest = BookingRequest(
            spotId = selected.id.filter { it.isDigit() }.toIntOrNull() ?: 0,
            plate_Char = vehicleData.plateChar,
            Plate_Num = vehicleData.PlateNum,
            startTime = formattedStartTime,
            endTime = formattedEndTime,
        )

        viewModelScope.launch {
            try {
                val response = apiService.Booking(bookingRequest)
                _bookingResponse.value = response.body()
                authInterceptor.setToken(response.body()?.token ?: "")
                _bookingConfirmed.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}
