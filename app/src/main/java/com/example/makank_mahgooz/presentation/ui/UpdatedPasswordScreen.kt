package com.example.makank_mahgooz.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.makank_mahgooz.R

class UpdatedPasswordScreen : Screen {
    @Composable
    override fun Content() {
        updatedPasswordScreen()
    }
    @Composable
    fun updatedPasswordScreen(){
        val navigator= LocalNavigator.currentOrThrow
        Surface(modifier = Modifier.fillMaxSize(), color= Color.White) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.checked),
                    contentDescription = null,
                    modifier = Modifier.size(98.dp))
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Successful",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semibold))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your password has been successfully updated.",
                    style = TextStyle(
                        color = Color.Black.copy(alpha = 0.25f),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Click Continue to login.",
                    style = TextStyle(
                        color = Color.Black.copy(alpha = 0.25f),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Continue button
                Button(onClick = {navigator.push(LoginScreen())},colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff004AAD),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xff004AAD).copy(alpha = 0.5f),
                    disabledContentColor = Color.White.copy(alpha = 0.5f)
                ), shape = RoundedCornerShape(0.dp), modifier = Modifier
                    .width(329.dp)
                    .height(50.dp),)
                {
                    Text(text = "Continue",style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_bold))
                    )
                    )
                }
            }
        }
    }
}