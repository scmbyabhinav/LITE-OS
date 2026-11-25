# AOSP integration

LITEOS uses the Android Open Source Project (AOSP) as its intended operating-system base. This repository currently contains a launcher prototype and integration scaffolding; it does **not** contain a synced AOSP checkout or a bootable product image.

## Upstream source selection

Use Google's `android-latest-release` manifest branch. On 25 November 2026, the AOSP documentation identifies its current target as `android17-release`. The moving alias is preferred for starting new development; record the resolved manifest revision for every reproducible build.

Official source initialization:

```bash
repo init --partial-clone --no-use-superproject \
  -b android-latest-release \
  -u https://android.googlesource.com/platform/manifest
repo sync -c -j8
```

Sources:
- https://source.android.com/docs/setup/start
- https://source.android.com/docs/setup/download
- https://source.android.com/docs/setup/reference/build-numbers

## Build capacity and CI decision

Google's current AOSP setup guidance lists at least **400 GB free disk** and **64 GB RAM** for a build workstation. A standard GitHub-hosted runner is not an appropriate place to launch a full AOSP checkout/build without a separately validated larger runner or remote build host.

Therefore:
- Keep the existing GitHub Actions workflow focused on the small launcher APK and unit tests.
- Do not attempt a full AOSP sync/build in that workflow.
- Evaluate a dedicated cloud build host only after a specific product target and device support plan exist.
- Preserve build provenance: manifest revision, project revisions, toolchain, build flags and artifact hashes.

## Repository boundaries

- Keep the AOSP source checkout outside this Git repository; use Repo manifests to assemble upstream projects.
- Keep LITEOS product overlays, product make fragments, device configuration and integration scripts here.
- `aosp/product/liteos_common.mk` is an initial product-property fragment, not a standalone lunch target.
- Do not add proprietary vendor blobs or redistribute device firmware without confirming applicable rights.
- Every physical device needs its own validated kernel/device tree/vendor interface and boot/recovery testing.

## Current status

| Area | Status |
|---|---|
| Launcher APK | Implemented; GitHub Actions build verified |
| Adaptive profile resolver | Implemented; JVM unit tests pass in CI |
| Shared platform/device contracts | Documented |
| AOSP upstream selection | `android-latest-release` (currently `android17-release`) |
| LITEOS common product properties | Initial make fragment added; not yet consumed by an AOSP product |
| AOSP source sync | Not performed |
| LITEOS AOSP lunch target | Not implemented |
| Device-specific ROM | Not implemented |
| Bootable image | Not available |

## Next integration gates

1. Create a Repo local manifest only when LITEOS-owned source projects exist.
2. Define and integrate a real AOSP product target.
3. Select one reference device for the first hardware port; universal support remains a family of device-specific builds.
4. Establish suitable cloud build capacity and capture immutable source revisions.
5. Add image validation, signing, recovery and rollback tests before publishing any flashable artifact.
