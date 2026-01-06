package com.example.makank_mahgooz.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.makank_mahgooz.presentation.viewmodel.VehiclesViewModel

class VehicleScreen(private val selectedSlot: ParkingSlots) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: VehiclesViewModel = hiltViewModel()

        var selectedVehicle by remember { mutableStateOf<VehicleDetails?>(null) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Your Vehicles",style = TextStyle(color = Color.White,
                        fontFamily = FontFamily(Font(R.font.poppins_medium))
                    )) },
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
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navigator.push(AddVehicleScreen(onVehicleAdded = {
                            viewModel.addVehicle(it)
                            selectedVehicle = it // Auto-select the newly added vehicle
                        }))
                    },
                    containerColor = Color(0xFF0047AB)
                ) {
                    Text("+", color = Color.White, fontSize = 24.sp)
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                viewModel.vehicles.forEach { vehicle ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { selectedVehicle = vehicle },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            RadioButton(
                                selected = selectedVehicle?.Id == vehicle.Id,
                                onClick = { selectedVehicle = vehicle },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "${vehicle.plateChar} - ${vehicle.PlateNum}")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        navigator.push(TimeSelectScreen(
                            vehicle = selectedVehicle, slot = selectedSlot
                        ))
                    },
                    enabled = selectedVehicle != null,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0047AB))
                ) {
                    Text("Continue", color = Color.White)
                }
            }
        }
    }
}