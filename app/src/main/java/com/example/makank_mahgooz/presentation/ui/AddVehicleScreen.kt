package com.example.makank_mahgooz.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.makank_mahgooz.R
import com.example.makank_mahgooz.data.model.VehicleDetails
import com.example.makank_mahgooz.presentation.viewmodel.VehiclesViewModel
import java.util.UUID

class AddVehicleScreen(
    private val onVehicleAdded: (VehicleDetails) -> Unit
) : Screen {
    @Composable
    override fun Content() {
        val vehiclesViewModel: VehiclesViewModel = hiltViewModel()
        AddVehicleScreenContent(onVehicleAdded = { vehicle ->
            vehiclesViewModel.addVehicle(vehicle)
            onVehicleAdded(vehicle)
        })
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("NotConstructor")
    @Composable
    fun AddVehicleScreenContent(
        onVehicleAdded: (VehicleDetails) -> Unit
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val characterValues = remember {
            listOf(
                mutableStateOf(""),
                mutableStateOf(""),
                mutableStateOf("")
            )
        }

        val numberValues = remember {
            listOf(
                mutableStateOf(""),
                mutableStateOf(""),
                mutableStateOf(""),
                mutableStateOf("")
            )
        }

        val focusRequesters = remember { List(7) { FocusRequester() } }
        val focusManager = LocalFocusManager.current

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Add Vehicle",
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            fontSize = 20.sp
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
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "License Plate",
                    color = Color(0xFF0047AB),
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Text(
                    text = "Characters",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    characterValues.forEachIndexed { index, textState ->
                        LicensePlateField(
                            value = textState.value,
                            onValueChange = { newValue ->
                                if (newValue.length <= 1) {
                                    textState.value = newValue
                                    if (newValue.isNotEmpty() && index < characterValues.size - 1) {
                                        focusRequesters[index + 1].requestFocus()
                                    } else if (newValue.isNotEmpty() && index == characterValues.size - 1) {
                                        focusRequesters[characterValues.size].requestFocus()
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            focusRequester = focusRequesters[index],
                            onNext = {
                                if (index < characterValues.size - 1) {
                                    focusRequesters[index + 1].requestFocus()
                                } else {
                                    focusRequesters[characterValues.size].requestFocus()
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Numbers",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    numberValues.forEachIndexed { index, textState ->
                        LicensePlateField(
                            value = textState.value,
                            onValueChange = { newValue ->
                                if (newValue.length <= 1 && (newValue.isEmpty() || newValue.all { it.isDigit() })) {
                                    textState.value = newValue
                                    if (newValue.isNotEmpty() && index < numberValues.size - 1) {
                                        focusRequesters[characterValues.size + index + 1].requestFocus()
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = if (index < numberValues.size - 1) ImeAction.Next else ImeAction.Done
                            ),
                            focusRequester = focusRequesters[characterValues.size + index],
                            onNext = {
                                if (index < numberValues.size - 1) {
                                    focusRequesters[characterValues.size + index + 1].requestFocus()
                                } else {
                                    focusManager.clearFocus()
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val plateChar = characterValues.joinToString(separator = " ") { it.value }
                        val plateNum = numberValues.joinToString(separator = "") { it.value }
                        val vehicleDetails = VehicleDetails(
                            plateChar = plateChar,
                            PlateNum = plateNum,
                            Id = UUID.randomUUID().toString()
                        )
                        onVehicleAdded(vehicleDetails)
                        navigator.pop()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0047AB)
                    ),
                    shape = RoundedCornerShape(4.dp),
                    enabled = characterValues.all { it.value.isNotEmpty() } &&
                            numberValues.all { it.value.isNotEmpty() }
                ) {
                    Text(
                        text = "Continue",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }

    @Composable
    fun LicensePlateField(
        value: String,
        onValueChange: (String) -> Unit,
        keyboardOptions: KeyboardOptions,
        focusRequester: FocusRequester,
        onNext: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .aspectRatio(1f)
                .focusRequester(focusRequester),
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xff004AAD),
                unfocusedBorderColor = Color(0xff004AAD)
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = androidx.compose.foundation.text.KeyboardActions(
                onNext = { onNext() },
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            maxLines = 1
        )
    }
}
