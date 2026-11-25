package com.liteos.launcher

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.content.res.Configuration

/**
 * Read-only hardware snapshot for LITEOS adaptive configuration.
 * This does not change Android system memory management or hardware settings.
 */
data class DeviceCapabilities(
    val totalRamBytes: Long,
    val isLowRamDevice: Boolean,
    val sdkInt: Int,
    val smallestWidthDp: Int,
    val supportedAbis: List<String>,
    val socModel: String?
) {
    val totalRamGb: Double
        get() = totalRamBytes.toDouble() / (1024.0 * 1024.0 * 1024.0)

    val isTablet: Boolean
        get() = smallestWidthDp >= 600

    companion object {
        fun detect(context: Context): DeviceCapabilities {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val memoryInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memoryInfo)

            val soc = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Build.SOC_MODEL.takeIf { it.isNotBlank() }
            } else {
                null
            }

            return DeviceCapabilities(
                totalRamBytes = memoryInfo.totalMem,
                isLowRamDevice = activityManager.isLowRamDevice,
                sdkInt = Build.VERSION.SDK_INT,
                smallestWidthDp = context.resources.configuration.smallestScreenWidthDp,
                supportedAbis = Build.SUPPORTED_ABIS.toList(),
                socModel = soc
            )
        }
    }
}

object LITEOSProfileResolver {
    const val AUTO = "auto"
    const val LOW_MEMORY = "low_memory"
    const val BALANCED = "balanced"
    const val PERFORMANCE = "performance"

    fun resolve(requestedProfile: String, device: DeviceCapabilities): String {
        return when (requestedProfile) {
            LOW_MEMORY -> LOW_MEMORY
            BALANCED -> BALANCED
            PERFORMANCE -> PERFORMANCE
            else -> if (device.isLowRamDevice || device.totalRamBytes <= 2_500_000_000L) {
                LOW_MEMORY
            } else {
                BALANCED
            }
        }
    }
}
