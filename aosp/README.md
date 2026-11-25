# AOSP integration

LITEOS uses the Android Open Source Project (AOSP) as its intended operating-system base. The repository currently contains the LITEOS launcher prototype and integration documentation; it does **not** contain a synced AOSP checkout or a bootable product image.

## Upstream source selection

As of this repository's current integration decision, use Google's moving `android-latest-release` manifest branch:

```bash
repo init -u https://android.googlesource.com/platform/manifest -b android-latest-release
repo sync -c -j${N}
```

The AOSP project documentation says this manifest tracks the latest public AOSP release branch. For reproducible release builds, record the resolved manifest revision and every local manifest/device-tree/vendor revision in the build provenance before compiling. Do not silently treat the moving branch name as an immutable version.

Official guidance:
- https://source.android.com/docs/setup/about/build-numbers
- https://source.android.com/docs/setup/download

## Build policy

- AOSP sync and full platform builds are separate from the lightweight launcher APK workflow.
- Do not run a full AOSP build on a standard GitHub-hosted runner without first validating disk, RAM, CPU time, artifact size and licensing constraints.
- Keep AOSP source outside this repository's Git history; use Repo manifests to assemble upstream projects.
- Keep LITEOS product overlays, product makefiles, device configuration and build scripts in this repository.
- Do not add proprietary vendor blobs or redistribute device firmware without confirming the applicable rights and redistribution terms.
- Every device target needs its own validated kernel/device tree/vendor interface and boot/recovery testing.

## Current status

| Area | Status |
|---|---|
| Launcher APK | Implemented; GitHub Actions build verified |
| Shared platform/device contracts | Documented |
| AOSP upstream selection | Selected: `android-latest-release` |
| AOSP source sync | Not yet performed |
| LITEOS AOSP product target | Not yet implemented |
| Device-specific ROM | Not yet implemented |
| Bootable image | Not available |

## Next integration gates

1. Add a version-controlled Repo local manifest for LITEOS-owned projects only after their source repositories exist.
2. Define the first AOSP product target and product identity overlays.
3. Select one reference device for the first hardware port; universal support remains a family of device-specific builds.
4. Establish a cloud build environment with adequate storage and memory, then capture immutable source revisions and build logs.
5. Add image validation, signing policy, recovery and rollback tests before publishing any flashable artifact.
