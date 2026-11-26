# LITEOS AOSP product configuration

This directory contains the shared product configuration fragment for the future AOSP integration.

## Files

- `liteos_common.mk`: common LITEOS product properties inherited by the emulator and future device products.

The first product definition lives in `vendor/liteos/products/liteos_x86_64.mk`. It inherits AOSP's `sdk_phone_x86_64` emulator product and adds the shared LITEOS properties. Its registration file exposes a userdebug lunch choice.

## Integration contract

The product makefile expects this shared fragment at `vendor/liteos/product/liteos_common.mk` inside an AOSP checkout. The repository validation workflow checks the expected source files and references, but does not sync AOSP, parse Android makefiles, or build an image.

The properties are namespaced under `ro.liteos.*` to avoid colliding with Android platform properties. They are initial defaults, not proof that the launcher currently reads them. The launcher still resolves its profile from Android's read-only device capability APIs and its local preference.

Do not add aggressive service removal, memory-killer settings, scheduler tweaks, or partition assumptions here. Those require measured device-specific evidence and platform review.
