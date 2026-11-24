# LITEOS

A lightweight Android experience built openly on GitHub.

## Current milestone: Launcher 0.1

This repository starts with a minimal native Android home application (launcher). It is **not yet a complete Android operating system or flashable ROM**.

- Native Android Views; no third-party UI/runtime libraries
- No network permission, analytics, telemetry, database, worker, or persistent service
- Displays and launches installed apps
- Phone/tablet responsive grid
- GitHub Actions builds a debug APK automatically

A 2 GB RAM device is a performance target to measure, not a compatibility guarantee. A launcher cannot control Android's kernel or overall system memory use; system-wide optimisation requires device-specific AOSP, kernel, and vendor integration.

## Get the APK

Open **Actions → Build LITEOS APK → latest successful run → Artifacts → liteos-debug-apk**.

The artifact is an installable debug APK, not firmware. Download and extract it, then open the APK on a test Android device. Android may require permission to install from your browser or file manager.

## Build

Push to `main` or run **Actions → Build LITEOS APK → Run workflow**. The cloud workflow compiles the APK and uploads it as a 14-day artifact. No local development software is needed for routine builds.

## Repository layout

- `app/` — launcher source and Android manifest
- `.github/workflows/` — cloud APK build
- `docs/ROADMAP.md` — milestones toward AOSP

## Licensing

Original LITEOS code is licensed under Apache-2.0. Android platform and SDK components retain their own licenses.
