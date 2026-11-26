# AOSP integration

LITEOS uses the Android Open Source Project (AOSP) as its intended operating-system base. This repository currently contains a launcher prototype and AOSP product scaffolding; it does **not** contain a synced AOSP checkout or a bootable product image.

## Upstream source

Use Google's `android-latest-release` manifest branch:

```bash
repo init --partial-clone -b android-latest-release -u https://android.googlesource.com/platform/manifest
repo sync -c -j8
```

Record the resolved manifest revision and all local project revisions for reproducible builds. The branch name is a moving alias, not an immutable revision.

Official guidance: https://source.android.com/docs/setup/start

## First product target

The initial integration target is an x86_64 Android emulator product:

- Product: `liteos_x86_64`
- Base product: AOSP `sdk_phone_x86_64`
- Variant: `userdebug`
- Product definition: `vendor/liteos/products/liteos_x86_64.mk`
- Registration: `vendor/liteos/products/AndroidProducts.mk`
- Shared properties: `aosp/product/liteos_common.mk`

After copying/overlaying the LITEOS files into an AOSP checkout, the intended build setup is:

```bash
source build/envsetup.sh
lunch liteos_x86_64-userdebug
m
```

The product inherits AOSP's emulator base. It is a software integration and boot-validation target, **not** a physical handset port. The Android source tree must contain the upstream `sdk_phone_x86_64` product.

## Build capacity and CI policy

Google's current AOSP setup guidance lists a 64-bit x86 Linux host, at least 400 GB free disk and at least 64 GB RAM. A standard GitHub-hosted runner is not an appropriate place to launch a full AOSP checkout/build.

- Keep the existing GitHub Actions workflow focused on the small launcher APK, unit tests and repository-level product validation.
- Do not attempt a full AOSP sync/build in that workflow.
- Evaluate a dedicated cloud build host only after validating its capacity and cost.
- Preserve build provenance: manifest revision, project revisions, toolchain, build flags and artifact hashes.

## Repository boundaries

- Keep the AOSP source checkout outside this Git repository; use Repo manifests to assemble upstream projects.
- Keep LITEOS product overlays, product make fragments, device configuration and integration scripts here.
- Do not add proprietary vendor blobs or redistribute device firmware without confirming applicable rights.
- Every physical device needs its own validated kernel/device tree/vendor interface and boot/recovery testing.

## Current status

| Area | Status |
|---|---|
| Launcher APK | Implemented; GitHub Actions build verified |
| Adaptive profile resolver | Implemented; JVM unit tests pass in CI |
| Shared platform/device contracts | Documented |
| AOSP upstream selection | `android-latest-release` |
| LITEOS common product properties | Added |
| x86_64 emulator product definition | Added; not yet parsed by AOSP |
| AOSP source sync | Not performed |
| AOSP product build | Not performed |
| Device-specific ROM | Not implemented |
| Bootable image | Not available |

## Next gates

1. Overlay these files into an AOSP checkout and validate the product registration with the real build system.
2. Build and boot the x86_64 emulator image.
3. Integrate the LITEOS launcher as a properly signed/prebuilt or source-built system app.
4. Select one physical reference device for the first hardware port.
5. Add image validation, signing, recovery and rollback tests before publishing any flashable artifact.
