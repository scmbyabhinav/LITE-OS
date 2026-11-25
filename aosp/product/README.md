# LITEOS AOSP product configuration

This directory contains the first shared product configuration fragment for the future AOSP integration.

## Files

- `liteos_common.mk`: common LITEOS product properties intended to be inherited by a real device/emulator product makefile.

## Integration contract

A future product makefile may include this fragment with:

```make
$(call inherit-product, vendor/liteos/product/liteos_common.mk)
```

The path above is the planned location in an AOSP source checkout. This repository's fragment must be copied or overlaid into that checkout by a future integration step; it is not automatically visible to AOSP today.

The properties are namespaced under `ro.liteos.*` to avoid colliding with Android platform properties. They are initial defaults, not proof that the launcher currently reads them. The launcher still resolves its profile from Android's read-only device capability APIs and its local preference.

Do not add aggressive service removal, memory-killer settings, scheduler tweaks, or partition assumptions here. Those require measured device-specific evidence and platform review.
