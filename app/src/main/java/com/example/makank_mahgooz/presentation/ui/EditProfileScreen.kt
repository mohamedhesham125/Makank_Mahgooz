package com.example.makank_mahgooz.presentation.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.makank_mahgooz.R
import com.example.makank_mahgooz.presentation.viewmodel.EditProfileViewmodel

class EditProfileScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: EditProfileViewmodel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        editProfileScreen(
            onEditClick = { firstName, lastName, phone, birthdate, image ->
                viewModel.updateProfile(
                    FirstName = firstName,
                    LastName = lastName,
                    Phone = phone,
                    Birthdate = birthdate,
                    Image = image,
                    oneditProfileSuccess = {
                        navigator.pop()
                    }
                )
            }
        )
    }

    @Composable
    fun editProfileScreen(onEditClick: (String, String, String, String, String) -> Unit) {
        var firstName by remember { mutableStateOf(TextFieldValue()) }
        var lastName by remember { mutableStateOf(TextFieldValue()) }
        var phone by remember { mutableStateOf(TextFieldValue()) }
        var birthdate by remember { mutableStateOf(TextFieldValue()) }
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

        val imageModel = remember { mutableStateOf<Any>(R.drawable.person) }

        LaunchedEffect(selectedImageUri) {
            selectedImageUri?.let {
                imageModel.value = it
            }
        }

        val singleImagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                selectedImageUri = uri
            }
        )

        val navigator = LocalNavigator.currentOrThrow

        Column(modifier = Modifier.padding(start = 17.dp, top = 30.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.clickable(onClick = { navigator.pop() })
                )
                Spacer(modifier = Modifier.width(89.dp))
                Text(
                    text = "Edit Profile",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    ),
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .aspectRatio(1f),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageModel.value)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .clip(CircleShape)
                            .matchParentSize(),
                        contentScale = ContentScale.Crop
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.camera_alt_24),
                        contentDescription = "Edit Photo",
                        modifier = Modifier
                            .background(Color.White, CircleShape)
                            .clip(CircleShape)
                            .clickable {
                                singleImagePickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                            .border(1.dp, Color.White, CircleShape)
                            .padding(6.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "first name",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                ))
            OutlinedTextField(value = firstName,
                onValueChange = { firstName = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff004AAD),
                    unfocusedBorderColor = Color(0xff004AAD)
                ),
                modifier = Modifier.width(329.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "last name",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                )
            )
            OutlinedTextField(value = lastName,
                onValueChange = { lastName = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff004AAD),
                    unfocusedBorderColor = Color(0xff004AAD)
                ),
                modifier = Modifier.width(329.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Phone Number",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                ))
            OutlinedTextField(value = phone,
                onValueChange = { phone = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff004AAD),
                    unfocusedBorderColor = Color(0xff004AAD)
                ),
                modifier = Modifier.width(329.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Birthdate",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                )
            )
            OutlinedTextField(value = birthdate,
                onValueChange = { birthdate = it },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff004AAD),
                    unfocusedBorderColor = Color(0xff004AAD)
                ),
                modifier = Modifier.width(329.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))
            GenderSelector()
            Spacer(modifier = Modifier.height(15.dp))
            Button(onClick = {
                onEditClick(
                    firstName.text,
                    lastName.text,
                    phone.text,
                    birthdate.text,
                    selectedImageUri?.toString() ?: ""
                )
            }, colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff004AAD),
                contentColor = Color.White,
                disabledContainerColor = Color(0xff004AAD).copy(alpha = 0.5f),
                disabledContentColor = Color.White.copy(alpha = 0.5f)
            ), shape = RoundedCornerShape(0.dp), modifier = Modifier
                .width(329.dp)
                .height(50.dp),
            ) {
                Text(text = "Update",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_bold))
                    )
                )
            }

        }
    }

    @Composable
    fun GenderSelector() {
        var selectedGender by remember { mutableStateOf("Male") }

        Text(
            text = "Gender",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GenderOption(
                text = "Male",
                selected = selectedGender == "Male",
                onSelect = { selectedGender = "Male" },
                modifier = Modifier.weight(1f)
            )

            GenderOption(
                text = "Female",
                selected = selectedGender == "Female",
                onSelect = { selectedGender = "Female" },
                modifier = Modifier.weight(1f)
            )
        }
    }

    @Composable
    fun GenderOption(
        text: String,
        selected: Boolean,
        onSelect: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Surface(
            color = Color.White,
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = modifier
                .selectable(
                    selected = selected,
                    onClick = onSelect
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xff004AAD),
                        unselectedColor = Color(0xff004AAD)
                    ),
                    selected = selected,
                    onClick = onSelect,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = text,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}
