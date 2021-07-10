package com.example.climate.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

/**
 *
 * Helper to check if a permission is enabled
 */
object PermissionHelper {

    /**
     * Helper to check if a permission is enabled
     * Asks for permission if it is already not granted
     */
    fun checkMyPermission(
        context: Activity,
        permissions: Array<String>,
        REQUEST_CODE: Int
    ): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        permissions[0]
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    context.requestPermissions(permissions, REQUEST_CODE)
                    break
                } else {
                    return true
                }
            }
        } else {
            return true
        }
        return false
    }
}