package com.example.makank_mahgooz.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.makank_mahgooz.R
import com.example.makank_mahgooz.presentation.viewmodel.SignUpViewModel
import java.util.regex.Pattern

class SignUpScreen : Screen {
    @Composable
    override fun Content() {
        val navigator= LocalNavigator.currentOrThrow
        val viewModel: SignUpViewModel = hiltViewModel()
        signUpScreen(
            onRegisterClick = { firstname,lastname,email, password ->
                viewModel.register(
                    firstname,
                    lastname,
                    email,
                    password,
                    onRegiserterSuccess = {
                        navigator.push(LoginScreen())
                    }
                )
            }
        )
    }
    @Composable
    fun signUpScreen(onRegisterClick: (String,String,String, String) -> Unit){
        val navigator= LocalNavigator.currentOrThrow
        var firstName by remember { mutableStateOf(TextFieldValue()) }
        var lastName by remember { mutableStateOf(TextFieldValue()) }
        var email by remember { mutableStateOf(TextFieldValue()) }
        var password by remember { mutableStateOf(TextFieldValue()) }
        var isAgreed by remember { mutableStateOf(false) }
        var userclicked by remember { mutableStateOf(false) }

        // Validation states
        var emailError by remember { mutableStateOf(false) }
        var passwordError by remember { mutableStateOf(false) }

        // Email validation regex
        val emailRegex = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$")

        // Password validation (minimum 6 characters)
        val isValidPassword = password.text.length >= 6

        Column(modifier = Modifier.padding(start = 16.dp, top = 25.dp)) {
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,){
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.clickable(onClick = { navigator.pop() })
                )
                Spacer(modifier = Modifier.width(85.dp))
                Text(text = "Sign Up", style = TextStyle(
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                ),)
            }
            // First name text field
            Text(text = "first name")
            OutlinedTextField(value =firstName ,
                onValueChange = {
                    firstName = it },
                label = { Text(text = "Enter Your first name",

                    color = Color(0xffA7AFB6) )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff004AAD),
                    unfocusedBorderColor = Color(0xff004AAD)
                ),
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.mail),
                    contentDescription = null,
                    tint = Color(0xffA7AFB6),
                    modifier = Modifier.size(20.dp)) },
                modifier = Modifier.width(329.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            //last name text field
            Text(text = "last name")
            OutlinedTextField(value =lastName ,
                onValueChange = {
                    lastName = it},
                label = { Text(text = "Enter Your last name",

                    color = Color(0xffA7AFB6) )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff004AAD),
                    unfocusedBorderColor = Color(0xff004AAD)
                ),
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.mail),
                    contentDescription = null,
                    tint = Color(0xffA7AFB6),
                    modifier = Modifier.size(20.dp)) },
                modifier = Modifier.width(329.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            //email text field
            Text(text = "Email")
            OutlinedTextField(value =email ,
                onValueChange = {
                    email = it
                    emailError = !emailRegex.matcher(it.text).matches() && it.text.isNotEmpty()},
                label = { Text(text = "Enter Your Email",

                    color = Color(0xffA7AFB6) )
                },
                isError = emailError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff004AAD),
                    unfocusedBorderColor = Color(0xff004AAD)
                ),
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.mail),
                    contentDescription = null,
                    tint = Color(0xffA7AFB6),
                    modifier = Modifier.size(20.dp)) },
                modifier = Modifier.width(329.dp)
            )
            if (emailError) {
                Text("Invalid email address", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(15.dp))

            //password text field
            Text(text = "password")
            OutlinedTextField(value =password ,
                onValueChange = {
                    password = it
                    passwordError = !isValidPassword && it.text.isNotEmpty()},
                label = { Text(text = "Enter Your Password",
                    color = Color(0xffA7AFB6)
                )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff004AAD),
                    unfocusedBorderColor = Color(0xff004AAD)
                ),
                isError = passwordError,
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.password),
                    contentDescription = null,
                    tint = Color(0xffA7AFB6),
                    modifier = Modifier.size(20.dp)) },
                modifier = Modifier.width(329.dp)
            )
            if (passwordError) {
                Text("Password must be at least 6 characters", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(15.dp))

            //remember me and forget password
            Row {
                Checkbox(checked = isAgreed, onCheckedChange = {
                    isAgreed=it
                },

                    colors = CheckboxDefaults.colors(
                    checkedColor = Color.Black,
                    uncheckedColor = Color.Black,
                    checkmarkColor = Color.White
                ), modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Row {
                    Text(
                        text = "Agree with", style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular))
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Terms & Conditions", style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_bold))
                    ))
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            // Sign Up button
            Button(onClick = {
                if (isValidPassword&& !emailError && !passwordError  && isAgreed)
                {
                    userclicked = true
                    onRegisterClick(firstName.text,lastName.text,email.text, password.text)
                }
            },
                colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff004AAD),
                contentColor = Color.White,
                disabledContainerColor = Color(0xff004AAD).copy(alpha = 0.5f),
                disabledContentColor = Color.White.copy(alpha = 0.5f)
            ), shape = RoundedCornerShape(0.dp), modifier = Modifier
                .width(329.dp)
                .height(50.dp),)
            {
                Text(text = "Sign Up",style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold))
                )
                )
            }
            Spacer(modifier = Modifier.height(38.dp))
            // or continue with
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(1.dp),
                    color = Color.Gray
                )
                Text(
                    text = "or continue with",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                HorizontalDivider(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(1.dp),
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Login with google and facebook
            Row(modifier = Modifier.padding(start = 14.5.dp)) {
                OutlinedButton(onClick = {},modifier = Modifier
                    .width(145.dp)
                    .height(41.dp)) {
                    Icon(painter = painterResource(id = R.drawable.google ), contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(25.dp))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Google",
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            fontSize = 14.sp,
                        )
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedButton(onClick = {}, modifier = Modifier
                    .width(145.dp)
                    .height(41.dp)) {
                    Icon(painter = painterResource(id = R.drawable.facebook ), contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "facebook",
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            fontSize = 14.sp,
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(71.dp))
            Row(modifier = Modifier.padding(start = 52.dp)) {
                Text(text = "Already have an account?",style = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                )
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Log in",style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    color = Color(0xff004AAD),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold))
                ),
                    modifier = Modifier.clickable(onClick = {navigator.push(LoginScreen())})
                )
                AnimatedVisibility(visible = userclicked) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}