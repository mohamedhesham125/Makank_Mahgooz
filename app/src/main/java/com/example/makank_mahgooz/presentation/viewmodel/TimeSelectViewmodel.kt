package com.example.makank_mahgooz.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TimeSelectViewmodel @Inject constructor() : ViewModel() {

    private val _startTime = MutableStateFlow<Calendar?>(null)
    val startTime: StateFlow<Calendar?> = _startTime

    private val _endTime = MutableStateFlow<Calendar?>(null)
    val endTime: StateFlow<Calendar?> = _endTime

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration

    private val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    fun setStartTime(cal: Calendar) {
        _startTime.value = cal
        recalculateDuration()
    }

    fun setEndTime(cal: Calendar) {
        _endTime.value = cal
        recalculateDuration()
    }

    fun getFormattedStart(): String {
        return startTime.value?.let {
            formatter.format(it.time)
        } ?: "Not selected"
    }

    fun getFormattedEnd(): String {
        return endTime.value?.let {
            formatter.format(it.time)
        } ?: "Not selected"
    }

    fun getFormattedDuration(): String {
        val minutes = _duration.value
        val hours = minutes / 60
        val mins = minutes % 60
        return if (hours > 0) "$hours hr $mins min" else "$mins min"
    }

    private fun recalculateDuration() {
        val start = _startTime.value
        val end = _endTime.value
        if (start != null && end != null) {
            val duration = calculateDuration(start, end)
            _duration.value = duration
        }
    }

    private fun calculateDuration(start: Calendar, end: Calendar): Long {
        val startMillis = start.timeInMillis
        val endMillis = end.timeInMillis
        return if (endMillis >= startMillis) {
            TimeUnit.MILLISECONDS.toMinutes(endMillis - startMillis)
        } else {
            TimeUnit.MILLISECONDS.toMinutes(endMillis + TimeUnit.DAYS.toMillis(1) - startMillis)
        }
    }
}
