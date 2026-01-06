package com.example.makank_mahgooz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makank_mahgooz.data.model.BookingResponse
import com.example.makank_mahgooz.data.networking.ApiServices
import com.example.makank_mahgooz.interceptor.AuthInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryBookingViewmodel @Inject constructor(
    private val apiService: ApiServices,
    val authInterceptor: AuthInterceptor
) : ViewModel() {

    private val _bookingHistory = MutableStateFlow<List<BookingResponse>>(emptyList())
    val bookingHistory: StateFlow<List<BookingResponse>> = _bookingHistory

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchBookingHistory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getBooking()
                if (response.isSuccessful) {
                    response.body()?.let { bookings ->
                        _bookingHistory.value = bookings
                        _error.value = null
                    } ?: run {
                        _error.value = "No booking history available."
                        _bookingHistory.value = emptyList()
                    }
                } else {
                    _error.value = "Failed to load booking history."
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
