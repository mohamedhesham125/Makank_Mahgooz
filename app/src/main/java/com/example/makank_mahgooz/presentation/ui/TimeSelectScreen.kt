package com.example.makank_mahgooz.presentation.ui

import android.app.TimePickerDialog
import java.util.Calendar
import android.widget.TimePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.makank_mahgooz.R
import androidx.core.graphics.toColorInt
import com.example.makank_mahgooz.data.model.ParkingSlots
import com.example.makank_mahgooz.data.model.VehicleDetails
import com.example.makank_mahgooz.presentation.viewmodel.TimeSelectViewmodel

class TimeSelectScreen(
    private val slot: ParkingSlots,
    private val vehicle: VehicleDetails?
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: TimeSelectViewmodel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        val startTime by viewModel.startTime.collectAsState()
        val endTime by viewModel.endTime.collectAsState()
        val duration by viewModel.duration.collectAsState()

        val durationFormatted = remember(duration) { viewModel.getFormattedDuration() }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Select Time",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.clickable { navigator.pop() }
                        )
                    }
                )
            }
        ) { paddingValues ->

            val showTimePicker: (Calendar?, (Calendar) -> Unit) -> Unit =
                { initialTime, onTimeSelected ->
                    val cal = initialTime ?: Calendar.getInstance()
                    val dialog = TimePickerDialog(
                        context,
                        { _: TimePicker, hour: Int, minute: Int ->
                            val picked = Calendar.getInstance()
                            picked.set(Calendar.HOUR_OF_DAY, hour)
                            picked.set(Calendar.MINUTE, minute)
                            onTimeSelected(picked)
                        },
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        false
                    )

                    dialog.setOnShowListener {
                        dialog.getButton(TimePickerDialog.BUTTON_POSITIVE)?.setTextColor("#004AAD".toColorInt())
                        dialog.getButton(TimePickerDialog.BUTTON_NEGATIVE)?.setTextColor("#004AAD".toColorInt())
                    }

                    dialog.show()
                }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        showTimePicker(startTime) { viewModel.setStartTime(it) }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
                ) {
                    Text("Select Start Time", color = Color.White)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        showTimePicker(endTime) { viewModel.setEndTime(it) }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
                ) {
                    Text("Select End Time", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Duration: $durationFormatted")

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    onClick = {
                        if (startTime != null && endTime != null && duration > 0L)
                        {
                            navigator.push(
                                PaymentScreen(
                                    startTimeParam = viewModel.getFormattedStart(),
                                    endTimeParam = viewModel.getFormattedEnd(),
                                    durationParam = viewModel.getFormattedDuration(),
                                    slot = slot,
                                    vehicle = vehicle
                                )
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Please select both start and end time",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1)),
                    modifier = Modifier
                        .width(329.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Confirm", color = Color.White)
                }
            }
        }
    }
}
