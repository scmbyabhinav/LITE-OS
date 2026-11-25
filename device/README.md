# Device Support Contract

Each supported device requires its own reviewed configuration and validated build. A single generic flashable image is not a realistic target across unrelated Android devices.

## Required device record

- Manufacturer and exact commercial model
- Device codename
- SoC/chipset and architecture
- RAM and storage variants
- Existing Android version/build fingerprint
- Bootloader unlock status and documented unlock path
- Kernel source/version and device-tree provenance
- Vendor firmware requirements and licensing notes
- Partition layout, dynamic partition and A/B status
- Recovery and rollback procedure
- Known hardware status: display, touch, audio, camera, Wi-Fi, Bluetooth, modem, GPS, sensors, biometrics, charging and encryption

## Support tiers

- **Experimental:** build or boot exploration only; no stability promise.
- **Community-supported:** community-maintained port with published limitations.
- **Tested:** repeatable install, core hardware, update, recovery and security checks completed for the exact device variant.

Never infer support for another regional, carrier or RAM variant from a similar model name.

## Reference configuration

See `device/configs/reference-device.example.json`. It is a documentation template, not a real device definition and not usable to build or flash firmware.
