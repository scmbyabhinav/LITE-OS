package com.liteos.launcher

import org.junit.Assert.assertEquals
import org.junit.Test

class LITEOSProfileResolverTest {
    private fun device(ramBytes: Long, lowRam: Boolean = false) = DeviceCapabilities(
        totalRamBytes = ramBytes,
        isLowRamDevice = lowRam,
        sdkInt = 35,
        smallestWidthDp = 360,
        supportedAbis = listOf("arm64-v8a"),
        socModel = null
    )

    @Test
    fun autoSelectsLowMemoryForLowRamFlag() {
        assertEquals(
            LITEOSProfileResolver.LOW_MEMORY,
            LITEOSProfileResolver.resolve(LITEOSProfileResolver.AUTO, device(6_000_000_000L, true))
        )
    }

    @Test
    fun autoSelectsLowMemoryAtTwoPointFiveBillionByteThreshold() {
        assertEquals(
            LITEOSProfileResolver.LOW_MEMORY,
            LITEOSProfileResolver.resolve(LITEOSProfileResolver.AUTO, device(2_500_000_000L))
        )
    }

    @Test
    fun autoSelectsBalancedAboveThreshold() {
        assertEquals(
            LITEOSProfileResolver.BALANCED,
            LITEOSProfileResolver.resolve(LITEOSProfileResolver.AUTO, device(4_000_000_000L))
        )
    }

    @Test
    fun explicitProfileOverridesAutomaticDetection() {
        assertEquals(
            LITEOSProfileResolver.PERFORMANCE,
            LITEOSProfileResolver.resolve(LITEOSProfileResolver.PERFORMANCE, device(2_000_000_000L, true))
        )
    }

    @Test
    fun unknownProfileFallsBackToAutomaticDetection() {
        assertEquals(
            LITEOSProfileResolver.LOW_MEMORY,
            LITEOSProfileResolver.resolve("unexpected", device(2_000_000_000L))
        )
    }
}
