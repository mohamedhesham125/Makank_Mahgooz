package com.example.makank_mahgooz.presentation.viewmodel

import android.app.Activity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private var _permissionGranted = mutableStateOf(false)
    val permissionGranted: State<Boolean> = _permissionGranted

    private var _permissionDenied = mutableStateOf(false)
    val permissionDenied: State<Boolean> = _permissionDenied

    private var _permanentlyDenied = mutableStateOf(false)
    val permanentlyDenied: State<Boolean> = _permanentlyDenied

    private var permissionHandler: LocationPermissionHandler? = null

    fun initPermissions(activity: Activity) {
        if (permissionHandler == null) {
            permissionHandler = LocationPermissionHandler(
                activity = activity,
                onPermissionGranted = { _permissionGranted.value = true },
                onPermissionDenied = { _permissionDenied.value = true },
                onPermanentlyDenied = { _permanentlyDenied.value = true }
            )
        }
        checkPermissions()
    }

    fun checkPermissions() {
        permissionHandler?.requestLocationPermission()
    }

    fun openAppSettings() {
        permissionHandler?.openAppSettings()
    }
}
