# LITEOS Roadmap

## Vision: one universal experience, many hardware targets

LITEOS is intended to serve low-end, mid-range and high-end Android phones and tablets. The product goal is a consistent, lightweight experience that adapts to available RAM, CPU/GPU capability, screen size, storage and hardware features.

**Universal does not mean one flashable image for every device.** Android devices have different chipsets, boot chains, kernels, device trees, vendor firmware, partition layouts and drivers. LITEOS will use a shared platform and a hardware adaptation layer, with device-specific builds where required.

## Architecture principles

- **Common LITEOS platform:** launcher, system experience, settings, update policy, accessibility and product identity.
- **Adaptive resource profiles:** low-memory, balanced and performance profiles; features scale to hardware rather than forcing one configuration.
- **Hardware abstraction and device support:** separate device configurations, kernel/device trees and vendor components for supported targets.
- **Compatibility tiers:** distinguish tested devices, community-supported devices and experimental ports. Never claim universal boot compatibility without testing.
- **Open and reproducible:** keep source, build instructions, device manifests and test results in GitHub.
- **Privacy and efficiency:** avoid unnecessary background services, telemetry and bundled bloat.

## 0.1 — Minimal launcher (built)

- Native Android UI with no third-party runtime libraries
- App discovery and launching
- No network permission or persistent background service
- GitHub Actions cloud-generated debug APK

This milestone is a launcher application, not firmware.

## 0.2 — Universal launcher experience

- App search and favorites
- Responsive phone, tablet and large-screen layouts
- Adaptive grid density and typography
- Theme, wallpaper and accessibility controls
- Verify behavior across Android versions and screen sizes

## 0.3 — Performance and compatibility lab

- Read-only device capability detection (RAM, low-RAM classification, Android API, screen class, CPU ABIs and available SoC model)
- Automatic low-memory/balanced profile selection with manual override
- Shared device-port and platform-layer contracts documented in `platform/` and `device/`



- Define low-end, mainstream and high-end device classes
- Measure cold start, idle memory, app launch latency, battery impact and storage
- Test under memory pressure and low storage
- Record exact device model, chipset, RAM, Android build and test method
- Publish compatibility matrix and known limitations

The 2 GB RAM class is an important optimisation target, not a universal minimum or guarantee. Higher-end devices should also run LITEOS and may use richer visual settings without compromising the lightweight default experience.

## 1.0 — AOSP platform foundation

- Select representative reference devices across different chipset families
- Establish an AOSP-based common platform and reproducible build pipeline
- Define device adaptation contracts and product configuration
- Verify bootloader, kernel, device tree, vendor firmware, partition and licensing requirements per device
- Build and test device-specific images before listing a device as supported

## 2.0 — Device expansion

- Add device families incrementally, prioritising maintainable upstreamable support
- Create automated build/test jobs where licensing and compute allow
- Publish install, recovery, rollback and security-update guidance per device
- Maintain stable, beta and experimental support tiers

## Release rule

No device is called supported until its build boots, core hardware works, updates and recovery are tested, and compatibility results are documented. A single generic ROM image for every Android device is not a realistic promise; a common LITEOS platform with validated device-specific builds is the scalable route.
