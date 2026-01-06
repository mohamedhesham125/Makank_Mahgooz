package com.example.makank_mahgooz.presentation.viewmodel

import android.app.Activity
import android.widget.Toast
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager

class LocationPermissionHandler(
    private var activity: Activity,
    private var onPermissionGranted: () -> Unit,
    private var onPermissionDenied: () -> Unit,
    private var onPermanentlyDenied: () -> Unit
) {

    private lateinit var permissionsManager: PermissionsManager
    private var denialCount = 0

    fun requestLocationPermission() {
        if (PermissionsManager.areLocationPermissionsGranted(activity)) {
            onPermissionGranted()
        } else {
            permissionsManager = PermissionsManager(object : PermissionsListener {
                override fun onExplanationNeeded(permissionsToExplain: List<String>) {
                    Toast.makeText(activity, "We need location permission", Toast.LENGTH_LONG).show()
                }

                override fun onPermissionResult(granted: Boolean) {
                    if (granted) {
                        onPermissionGranted()
                    } else {
                        denialCount++
                        if (denialCount >= 2) {
                            onPermanentlyDenied()
                        } else {
                            onPermissionDenied()
                        }
                    }
                }
            })
            permissionsManager.requestLocationPermissions(activity)
        }
    }

    fun openAppSettings() {
        val intent = android.content.Intent().apply {
            action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = android.net.Uri.fromParts("package", activity.packageName, null)
        }
        activity.startActivity(intent)
    }
}
