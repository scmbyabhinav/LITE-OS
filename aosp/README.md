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
- Shared properties: `vendor/liteos/product/liteos_common.mk`

This is a software integration and boot-validation target, **not** a physical handset port. The Android source tree must contain the upstream `sdk_phone_x86_64` product.

## Apply the overlay

From this repository, point the script at an already-synced AOSP checkout. It checks that the target is an AOSP tree and that the x86_64 base product exists. It is safe to re-run: identical files are skipped and conflicting files are never overwritten.

Preview first:

```bash
bash scripts/apply-aosp-overlay.sh --dry-run /path/to/aosp
```

Apply:

```bash
bash scripts/apply-aosp-overlay.sh /path/to/aosp
cd /path/to/aosp
source build/envsetup.sh
lunch liteos_x86_64-userdebug
```

The script copies only the three LITEOS product files. It does not sync AOSP, modify upstream files, run lunch, or build an image. Product parsing and build validation must happen inside the real AOSP checkout.

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
| LITEOS common product properties | Added; SELinux/property-context integration still needs validation |
| x86_64 emulator product definition | Added; not yet parsed by AOSP |
| Overlay application script | Added; repository validation only |
| AOSP source sync | Not performed |
| AOSP product build | Not performed |
| Device-specific ROM | Not implemented |
| Bootable image | Not available |

## Next gates

1. Apply the overlay to a real AOSP checkout and validate product registration with the build system.
2. Resolve any product/property namespace and SELinux policy requirements.
3. Build and boot the x86_64 emulator image.
4. Integrate the LITEOS launcher as a properly signed/prebuilt or source-built system app.
5. Select one physical reference device for the first hardware port.
6. Add image validation, signing, recovery and rollback tests before publishing any flashable artifact.
