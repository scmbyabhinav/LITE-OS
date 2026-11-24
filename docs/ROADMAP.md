# LITEOS Roadmap

## Product definition

The first deliverable is a lightweight Android launcher APK. It is not a custom ROM or operating system firmware. We will validate the launcher and memory footprint before attempting device-specific AOSP integration.

## 0.1 — Minimal launcher

- Native Android UI with no third-party runtime libraries
- App discovery and launching
- No network permission or background services
- Cloud-generated debug APK

## 0.2 — Usability

- App search
- Phone/tablet responsive layout
- Favorites
- Theme and wallpaper controls
- Accessibility and large-text checks

## 0.3 — Performance

- Measure cold start, idle memory, and app launch latency
- Test low-memory conditions on real 2 GB devices
- Avoid persistent background work
- Record device, Android version, and measurement method

## 1.0 — AOSP investigation

- Select one exact reference device
- Confirm bootloader, kernel, device tree, firmware and vendor support
- Review AOSP release and license requirements
- Establish a reproducible base build before adding LITEOS branding

A 2 GB target is a measurable performance goal, not a guarantee across all hardware. Full AOSP builds are large and may exceed default hosted runner resources; identify suitable compute and a supported device before starting a ROM build.
