package com.example.makank_mahgooz.presentation.viewmodel

import android.app.Activity
import android.os.Build
import android.widget.Toast
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager

class LocationPermissionHelper(
    private val activity: Activity,
    private val onPermissionGranted: () -> Unit,
    private val onPermissionDenied: () -> Unit,
    private val onPermanentlyDenied: () -> Unit // إضافة معامل لإدارة الرفض الدائم
) {

    private lateinit var permissionsManager: PermissionsManager
    private var denialCount = 0 // عداد لعدد مرات الرفض

    fun checkAndRequestPermissions() {
        if (PermissionsManager.areLocationPermissionsGranted(activity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                !PermissionsManager.isBackgroundLocationPermissionGranted(activity)) {
                requestBackgroundLocationPermission() // طلب إذن الموقع في الخلفية للأندرويد 10 وأعلى
            } else {
                onPermissionGranted()
            }
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
                            onPermanentlyDenied() // لو تم الرفض أكثر من مرة
                        } else {
                            onPermissionDenied()
                        }
                    }
                }
            })
            permissionsManager.requestLocationPermissions(activity)
        }
    }

    private fun requestBackgroundLocationPermission() {
        Toast.makeText(activity, "We need background location permission", Toast.LENGTH_LONG).show()
        val intent = android.content.Intent().apply {
            action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = android.net.Uri.fromParts("package", activity.packageName, null)
        }
        activity.startActivity(intent)
    }

    // دالة لفتح إعدادات التطبيق في حالة الرفض الدائم
    fun openAppSettings() {
        val intent = android.content.Intent().apply {
            action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = android.net.Uri.fromParts("package", activity.packageName, null)
        }
        activity.startActivity(intent)
    }
}
