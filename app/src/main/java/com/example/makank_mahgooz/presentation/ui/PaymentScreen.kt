package com.example.makank_mahgooz.presentation.ui

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
import com.example.makank_mahgooz.data.model.ParkingSlots
import com.example.makank_mahgooz.data.model.VehicleDetails
import com.example.makank_mahgooz.presentation.viewmodel.PaymentViewmodel

class PaymentScreen(
    private val startTimeParam: String,
    private val endTimeParam: String,
    private val durationParam: String,
    private val slot: ParkingSlots,
    private val vehicle: VehicleDetails?
) : Screen {

    @Composable
    override fun Content() {
        val viewModel: PaymentViewmodel = hiltViewModel()
        val startTime by viewModel.startTime.collectAsState()
        val endTime by viewModel.endTime.collectAsState()
        val duration by viewModel.duration.collectAsState()
        val selectedVehicle by viewModel.vehicle.collectAsState()
        val selectedSlot by viewModel.selectedSlot.collectAsState()
        val bookingConfirmed by viewModel.bookingConfirmed.collectAsState()

        val navigator = LocalNavigator.currentOrThrow
        var showDialog by remember { mutableStateOf(false) }

        // ✅ Apply parameters to the ViewModel once
        LaunchedEffect(Unit) {
            viewModel.setStartTime(startTimeParam)
            viewModel.setEndTime(endTimeParam)
            viewModel.setDuration(durationParam)
            vehicle?.let { viewModel.setVehicle(it) }
            viewModel.selectSlot(slot.id)
        }

        if (bookingConfirmed && !showDialog) {
            showDialog = true
        }

        PaymentScreenContent(
            startTime = startTime,
            endTime = endTime,
            duration = duration,
            vehicle = selectedVehicle,
            slotId = selectedSlot?.id ?: "",
            onConfirm = { viewModel.sendBookingRequest() }
        )

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        navigator.push(HistoryBookingScreen())
                    }) {
                        Text("Go to Booking History")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        navigator.push(HomeScreen())
                    }) {
                        Text("Back to Home")
                    }
                },
                title = {
                    Text("Booking Confirmed")
                },
                text = {
                    Text("Your booking has been confirmed successfully.")
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PaymentScreenContent(
        vehicle: VehicleDetails?,
        slotId: String,
        startTime: String,
        endTime: String,
        duration: String,
        onConfirm: () -> Unit
    ) {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Booking Details",
                            fontFamily = FontFamily(Font(R.font.poppins_bold))
                        )
                    },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable { navigator.pop() }
                        )
                    }
                )
            }
        ) { paddingValues ->
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Mansoura Parking",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                            )
                        )
                        Text(
                            text = "154 , Gehan Street",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                color = Color(0xffA7AFB6)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        InfoRow("Vehicle:", "${vehicle?.plateChar ?: ""} - ${vehicle?.PlateNum ?: ""}")
                        InfoRow("PARKING LOT:", slotId)
                        InfoRow("Start:", startTime)
                        InfoRow("End:", endTime)
                        InfoRow("Duration:", duration)
                        InfoRow("Total:", "80 EGP")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onConfirm,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0057B8))
                ) {
                    Text(
                        text = "Pay & Confirm",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold))
                    )
                }
            }
        }
    }

    @Composable
    fun InfoRow(label: String, value: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontFamily = FontFamily(Font(R.font.poppins_medium))
            )
            Text(
                text = value,
                color = Color(0xFF0057B8),
                fontFamily = FontFamily(Font(R.font.poppins_medium))
            )
        }
    }
}
