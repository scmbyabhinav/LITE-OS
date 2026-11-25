# LITEOS

**One lightweight Android experience. Low-end to high-end. Phones and tablets.**

LITEOS aims to provide a consistent, efficient experience across a broad range of Android hardware, adapting to device capability rather than assuming every device is identical.

## Universal vision, honest compatibility

LITEOS is designed for:
- **Low-end devices:** prioritise responsiveness, low memory overhead and minimal background activity.
- **Mid-range devices:** balanced defaults and broad everyday functionality.
- **High-end devices:** retain the lightweight foundation while adapting layouts and optional visual capabilities.
- **Phones and tablets:** responsive interfaces for different screen sizes and densities.

A universal experience does **not** mean one firmware image can boot on every device. Chipsets, kernels, drivers, vendor firmware, bootloaders and partition layouts differ. The long-term plan is a shared LITEOS/AOSP platform with device-specific hardware adaptation and validated builds.

## Current milestone: Launcher 0.1

This repository currently contains a minimal native Android home application. It is **not yet a complete operating system or flashable ROM**.

- Native Android Views; no third-party UI/runtime libraries
- No network permission, analytics, telemetry, database, worker, or persistent service
- Displays and launches installed apps
- Phone/tablet responsive grid
- GitHub Actions builds a debug APK automatically

A 2 GB RAM class is a key optimisation target, not a compatibility guarantee. Actual memory use and performance must be measured on physical devices.

## Get the APK

Open **Actions → Build LITEOS APK → latest successful run → Artifacts → liteos-debug-apk**.

The artifact is an installable debug launcher APK, not firmware. Download and extract it, then install it on a test Android device.

## Build

Push to `main` or run **Actions → Build LITEOS APK → Run workflow**. Routine launcher builds happen in GitHub Actions; no local Android Studio installation is required.

## Repository layout

- `app/` — launcher source and Android manifest
- `.github/workflows/` — cloud APK build
- `docs/ROADMAP.md` — universal platform and AOSP roadmap

## Licensing

Original LITEOS code is licensed under Apache-2.0. Android platform and SDK components retain their own licenses.
