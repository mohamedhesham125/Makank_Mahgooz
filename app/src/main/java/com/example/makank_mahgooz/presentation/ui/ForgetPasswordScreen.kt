package com.example.makank_mahgooz.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.makank_mahgooz.R

class ForgetPasswordScreen: Screen {
    @Composable
    override fun Content() {
        forgetPasswordScreen()
    }

    @Composable
    fun forgetPasswordScreen(){
        val navigator= LocalNavigator.currentOrThrow
        var email by remember { mutableStateOf("")}
        IconButton(onClick = { navigator.pop() })
        {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null ,
                modifier = Modifier.padding(start = 10.dp, top = 16.dp)
            )
        }
        Column(modifier = Modifier.padding(start = 16.dp, top = 53.dp)) {
            Text(text = "Forget Password",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold))
            ))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Please Enter Your Email To Reset The password",
                style = TextStyle(
                    color = Color.Black.copy(0.35f),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold))
                ))
            Spacer(modifier = Modifier.height(34.dp))
            //email text field
            Text(text = "Email")
            OutlinedTextField(value =email ,
                onValueChange = {email = it},
                label = { Text(text = "Enter Your Email",
                    color =Color(0xffA7AFB6) )},
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.password),
                    contentDescription = null,
                    tint = Color(0xffA7AFB6),
                    modifier = Modifier.size(20.dp)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xff004AAD),
                    unfocusedBorderColor = Color(0xff004AAD)
                ),
                modifier = Modifier.width(329.dp)
            )
            Spacer(modifier = Modifier.height(34.dp))
            // Reset password button
            Button(onClick = {navigator.push(ResetPasswordScreen())},colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff004AAD),
                contentColor = Color.White,
                disabledContainerColor = Color(0xff004AAD).copy(alpha = 0.5f),
                disabledContentColor = Color.White.copy(alpha = 0.5f)
            ), shape = RoundedCornerShape(0.dp), modifier = Modifier
                .width(329.dp)
                .height(50.dp),
                enabled = email.isNotEmpty())
            {
                Text(text = "Reset Password",style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold))
                ))
            }

        }
    }

}