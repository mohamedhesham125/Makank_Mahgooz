package com.example.makank_mahgooz.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.makank_mahgooz.data.model.SlotStatus
import com.example.makank_mahgooz.presentation.viewmodel.ParkingSlotViewmodel

class ParkingSlotScreen : Screen {
    @Composable
    override fun Content() {
        parkingslotscreen()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun parkingslotscreen() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ParkingSlotViewmodel = hiltViewModel()
        val slots by viewModel.slots.collectAsState()
        val selectedSlot by viewModel.selectedSlot.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Select Spot",
                            modifier = Modifier.padding(start = 70.dp),
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1f)
                        .padding(15.dp)
                        .fillMaxSize()
                ) {
                    items(slots) { slot ->
                        ParkingSlotItem(slot = slot) {
                            viewModel.selectSlot(slot.id)
                        }
                    }
                }

                Button(
                    onClick = {
                        selectedSlot?.let { slot ->
                            navigator.push(VehicleScreen(slot))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff004AAD)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Continue",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            color = Color.White
                        )
                    )
                }
            }
        }
    }

    @Composable
    fun ParkingSlotItem(slot: ParkingSlots, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .clickable { onClick() }
                .border(2.dp, StateSlotColor(slot))
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = slot.id,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    color = if (slot.status == SlotStatus.SELECTED) StateSlotColor(slot) else Color.Black
                )
            )
        }
    }

    @Composable
    fun StateSlotColor(slot: ParkingSlots): Color {
        return when (slot.status) {
            SlotStatus.AVAILABLE -> Color.Gray
            SlotStatus.SELECTED -> Color(0xFF0047AB)
            SlotStatus.LOCKED -> Color(0xFFB22222)
        }
    }
}
