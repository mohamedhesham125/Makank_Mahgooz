package com.example.makank_mahgooz.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.makank_mahgooz.R
import com.example.makank_mahgooz.data.model.GetProfileResponse
import com.example.makank_mahgooz.presentation.viewmodel.ProfileViewmodel

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: ProfileViewmodel = hiltViewModel()
        val user = viewModel.userRespone.collectAsState()
        val updatedImage by viewModel.updatedImageUri.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        profileScreen(
            user = user.value,
            profileImage = updatedImage ?: R.drawable.profile,
            onEditClick = {
                navigator.push(EditProfileScreen())
            }
        )
    }

    @Composable
    fun profileScreen(user: GetProfileResponse?, profileImage: Any, onEditClick: () -> Unit) {
        val navigator= LocalNavigator.currentOrThrow
        var selectedItem by remember { mutableIntStateOf(2) }
        Scaffold(bottomBar = {
            NavigationBar {
                listOf("Home" to Icons.Default.Home, "Bookings" to R.drawable.booking_icon, "Profile" to Icons.Default.Person).forEachIndexed { index, (label, icon) ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            when (label) {
                                "Home" -> navigator.push(HomeScreen())
                                "Bookings" -> navigator.push(HistoryBookingScreen())
                                "Profile" -> navigator.push(ProfileScreen())
                            }
                        },
                        icon = {
                            when (icon) {
                                is Int -> Icon(
                                    painter = painterResource(id = icon),
                                    contentDescription = label,
                                    modifier = Modifier.size(20.dp),
                                    tint = if (selectedItem == index) Color(0xff004AAD) else Color.LightGray
                                )
                                else -> Icon(
                                    imageVector = icon as ImageVector,
                                    contentDescription = label,
                                    tint = if (selectedItem == index) Color(0xff004AAD) else Color.LightGray
                                )
                            }
                        },
                        label = { Text(label) }
                    )
                }
            }
        }){
            padding ->

        Column(modifier = Modifier.padding(start = 14.dp, top = 35.dp, end = 18.dp)) {
            Text(text = "Profile",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(27.dp))
            ProfileCard(
                firstname = user?.firstName,
                lastname = user?.lastName,
                email = user?.email,
                profileImage = profileImage,
                onEditClick = { onEditClick() }
            )
            Spacer(modifier = Modifier.height(45.dp))

            //account
            Row(modifier = Modifier.fillMaxWidth()){
              Icon(painter = painterResource(id = R.drawable.baseline_account_box_24),tint = Color(0xff004AAD), contentDescription = null)
              Spacer(modifier = Modifier.width(10.dp))
              Text(text = "Account",
                  style = TextStyle(
                      fontFamily = FontFamily(Font(R.font.poppins_regular)),
                      fontSize = 14.sp,
                  )
              )
                Spacer(modifier = Modifier.weight(1f))
                Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null,
                    tint = Color(0xff004AAD),
                    modifier = Modifier.size(17.dp)
                        .clickable { onEditClick() })
          }
            Spacer(modifier = Modifier.height(15.dp))


            //payment methods
            Row(modifier = Modifier.fillMaxWidth()){
                Icon(painter = painterResource(id = R.drawable.card_24),tint = Color(0xff004AAD), contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Payment methods",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 14.sp,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null,
                    tint = Color(0xff004AAD),
                    modifier = Modifier.size(17.dp)
                        .clickable {  })
            }
            Spacer(modifier = Modifier.height(15.dp))

            //vehicles
            Row(modifier = Modifier.fillMaxWidth()){
                Icon(painter = painterResource(id = R.drawable.car_24),tint = Color(0xff004AAD), contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "vehicles",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 14.sp,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null,
                    tint = Color(0xff004AAD),
                    modifier = Modifier.size(17.dp)
                        .clickable {})
            }
            Spacer(modifier = Modifier.height(15.dp))

            //app feedback
            Row(modifier = Modifier.fillMaxWidth()){
                Icon(painter = painterResource(id = R.drawable.comment_24), tint = Color(0xff004AAD), contentDescription = null,
                    modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "App feedback",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 14.sp,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null,
                    tint = Color(0xff004AAD),
                    modifier = Modifier.size(17.dp)
                        .clickable {  })
            }
            Spacer(modifier = Modifier.height(15.dp))

            Row(modifier = Modifier.fillMaxWidth().clickable {  }){
                Icon(painter = painterResource(id = R.drawable.logout), tint = Color(0xffFF0303), contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Log out",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 14.sp,
                    )
                )
            }
        }
        }


    }


    @Composable
    fun ProfileCard(
        firstname: String?,
        lastname: String?,
        email: String?,
        profileImage: Any?,
        onEditClick: () -> Unit
    ) {
        Card(
            colors = cardColors(containerColor = Color(0xff004AAD)),
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
               AsyncImage(
                   model = profileImage,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "$firstname $lastname",
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "$email",
                        style = TextStyle(
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    )
                }

                IconButton(onClick = onEditClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_edit_24),
                        contentDescription = "Edit Profile",
                        tint = Color.White
                    )
                }
            }
        }
    }

}