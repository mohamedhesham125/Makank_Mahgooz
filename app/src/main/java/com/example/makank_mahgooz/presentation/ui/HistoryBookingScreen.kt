package com.example.makank_mahgooz.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.makank_mahgooz.R
import com.example.makank_mahgooz.presentation.viewmodel.HistoryBookingViewmodel

class HistoryBookingScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: HistoryBookingViewmodel = hiltViewModel()
        val bookings by viewModel.bookingHistory.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val error by viewModel.error.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            viewModel.fetchBookingHistory()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Booking History",
                            fontFamily = FontFamily(Font(R.font.poppins_bold))
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                when {
                    isLoading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    error != null -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = error ?: "Unknown Error", color = Color.Red)
                        }
                    }
                    bookings.isEmpty() -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "No bookings found.")
                        }
                    }
                    else -> {
                        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                            items(bookings) { booking ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        val spacedPlateChar = booking.plate_Char.toCharArray()
                                            .joinToString(" ")
                                        val spacedPlateNum = booking.Plate_Num.toCharArray()
                                            .joinToString(" ")
                                        Text(
                                            text = "Vehicle: $spacedPlateChar - $spacedPlateNum",
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily.Default
                                        )
                                        Text("Spot ID: ${booking.spotId}", fontSize = 14.sp, color = Color.Gray)
                                        Text("Start: ${booking.startTime}", fontSize = 14.sp)
                                        Text("End: ${booking.endTime}", fontSize = 14.sp)
                                        Text("Duration: ${booking.duration}", fontSize = 14.sp)
                                        Text("Status: ${booking.status}", fontSize = 14.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
