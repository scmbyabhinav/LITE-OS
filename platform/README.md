# LITEOS Platform Layer

This directory defines the shared product/platform boundary for the future AOSP-based LITEOS system.

## Current state

The repository currently contains an Android launcher APK. It is not an AOSP product, system image, recovery image, kernel or flashable ROM.

## Intended layers

- `launcher/`: user-facing home experience (currently implemented in `app/`)
- `configuration/`: profile and product defaults shared by platform components
- `system/`: future privileged/system components, added only when justified
- `build/`: reproducible platform build orchestration and provenance

## Rules

1. Keep the common LITEOS experience independent from device-specific implementation.
2. Device capabilities are inputs to configuration, not permission to alter kernel or vendor behaviour.
3. Do not add persistent background services without a measured user benefit.
4. Keep network access, telemetry and bundled services opt-in and documented.
5. Never publish a device as supported until boot, hardware, recovery, update and security checks pass.

AOSP integration will be introduced in a separate, pinned source manifest and build environment. This README does not imply that AOSP sources are already present.
