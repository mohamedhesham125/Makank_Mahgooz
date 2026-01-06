package com.example.makank_mahgooz.presentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.BitmapFactory
import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.makank_mahgooz.R
import com.example.makank_mahgooz.presentation.viewmodel.HomeViewModel
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        HomeScreenContent()
    }

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun HomeScreenContent() {
    val sheetState = rememberModalBottomSheetState()
    var showGarageSheet by remember { mutableStateOf(false) }
    val navigator= LocalNavigator.currentOrThrow
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val activity = context as? Activity

    val permissionGranted by viewModel.permissionGranted
    val permissionDenied by viewModel.permissionDenied
    val permanentlyDenied by viewModel.permanentlyDenied

    val snackbarHostState = remember { SnackbarHostState() }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var userLocation by remember { mutableStateOf<Location?>(null) }
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var selectedItem by remember { mutableIntStateOf(0) }

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(activity) {
        activity?.let { viewModel.initPermissions(it) }
    }

    LaunchedEffect(permissionDenied) {
        if (permissionDenied) {
            val result = snackbarHostState.showSnackbar(
                message = "Location permission denied",
                actionLabel = "Retry"
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewModel.checkPermissions()
            }
        }
    }

    LaunchedEffect(permanentlyDenied) {
        if (permanentlyDenied) {
            val result = snackbarHostState.showSnackbar(
                message = "Permission permanently denied",
                actionLabel = "Settings"
            )
            if (result == SnackbarResult.ActionPerformed) {
                viewModel.openAppSettings()
            }
        }
    }

    fun addPin(mapView: MapView, point: Point, drawableId: Int) {
        val annotationApi = mapView.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()
        val bitmap = BitmapFactory.decodeResource(context.resources, drawableId)

        pointAnnotationManager.create(
            PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(bitmap)
        )
    }

    Scaffold(
        contentColor = Color.White,
        containerColor = Color(0xFF1565C0),

        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
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
                                    imageVector = icon as androidx.compose.ui.graphics.vector.ImageVector,
                                    contentDescription = label,
                                    tint = if (selectedItem == index) Color(0xff004AAD) else Color.LightGray
                                )
                            }
                        },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        if (permissionGranted) {
            Box(Modifier.padding(padding).fillMaxSize()) {
                if (showGarageSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { showGarageSheet = false },
                        sheetState = sheetState,
                        containerColor = Color.White,
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(text = "Details", style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            )
                            ,
                                modifier = Modifier.align(Alignment.CenterHorizontally))
                            Image(
                                painter = painterResource(id = R.drawable.garage),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Mansoura Parking", style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_semibold)),))
                            Text("154 , Gehan Street", style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                color = Color(0xFFA7AFB6)
                            ))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Description", style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_bold)),))
                            Text("Our parking slot offers a secure, well-maintained, and easily accessible space for customers. Designed with clearly marked lanes,and smooth surfaces. it ensures a hassle-free parking experience. Equipped with bright lighting, security cameras, it provides both safety and modern convenience.",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                    color = Color(0xFFA7AFB6)
                                ))
                            Text("Price per hour:", style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_bold)),))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("20 EGP", style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                color = Color(0xFF004AAD)
                            ))
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                OutlinedButton(onClick = { showGarageSheet = false },
                                    modifier = Modifier
                                        .width(160.dp)
                                        .height(48.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                                    Text(text = "Cancel", color = Color.Black)
                                }
                                Spacer(modifier =Modifier.width( 8.dp))
                                Button(
                                    onClick = { navigator.push(ParkingSlotScreen())},
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004AAD))
                                ) {
                                    Text(text = "Book Now", color = Color.White)
                                }
                            }
                        }
                    }
                }

                Column(Modifier.fillMaxSize()
                    .background(Color(0xFF004AAD))) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                                .background(Color(0xFF467ABF))
                        ) {
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Search location", color = Color.White) },
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    cursorColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.weight(1f)

                            )
                            Icon(
                                imageVector = Icons.Default.Search,
                                tint = Color.White,
                                contentDescription = null,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }

                    AndroidView(factory = { ctx ->
                        MapView(ctx).apply {mapView = this
                            val mapboxMap = mapboxMap
                            // Always load the style first
                            mapboxMap.loadStyle("mapbox://styles/hshm/cma121y10008l01s32gswbi8s/draft"){
                                mapboxMap.addOnMapClickListener { point ->
                                    // لما المستخدم يضغط على الماب
                                    showGarageSheet = true // يظهر البوتوم شيت
                                    true
                                }
                            }


                            location.updateSettings {
                                enabled = true
                            }

                            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                                location?.let {
                                    userLocation = it
                                    mapboxMap.setCamera(
                                        CameraOptions.Builder()
                                            .center(Point.fromLngLat(it.longitude, it.latitude))
                                            .zoom(14.0)
                                            .build()
                                    )
                                    addPin(this, Point.fromLngLat(it.longitude, it.latitude), R.drawable.user_location_triangle)
                                } ?: run {
                                    mapboxMap.setCamera(
                                        CameraOptions.Builder()
                                            .center(Point.fromLngLat(31.03771, 31.36154))
                                            .zoom(10.0)
                                            .build()
                                    )
                                }
                            }
                        }
                    }, modifier = Modifier.weight(1f))
                }

                FloatingActionButton(
                    containerColor = Color.White,
                    onClick = {
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            location?.let {
                                userLocation = it
                                mapView?.mapboxMap?.setCamera(
                                    CameraOptions.Builder()
                                        .center(Point.fromLngLat(it.longitude, it.latitude))
                                        .zoom(16.0)
                                        .build()
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.user_location_triangle),
                        contentDescription = "Current Location",
                        tint = Color.Black
                    )
                }
            }
        } else {
            Box(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Waiting for location permission...")
            }
        }
    }
}
}
