#!/usr/bin/env bash
set -euo pipefail

usage() {
  cat <<'USAGE'
Usage: scripts/apply-aosp-overlay.sh [--dry-run] /path/to/aosp

Copies the LITEOS x86_64 product overlay into an existing AOSP checkout.
Existing identical files are left untouched. Conflicting files are never
overwritten automatically.
USAGE
}

DRY_RUN=false
if [[ "${1:-}" == "--help" || "${1:-}" == "-h" ]]; then usage; exit 0; fi
if [[ "${1:-}" == "--dry-run" ]]; then DRY_RUN=true; shift; fi
[[ $# -eq 1 ]] || { usage >&2; exit 2; }

AOSP_ROOT="$(cd "$1" && pwd)"
REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

[[ -f "$AOSP_ROOT/build/envsetup.sh" ]] || {
  echo "Not an AOSP checkout: build/envsetup.sh is missing." >&2; exit 1;
}
[[ -f "$AOSP_ROOT/build/make/target/product/sdk_phone_x86_64.mk" ]] || {
  echo "AOSP x86_64 emulator base product was not found." >&2; exit 1;
}

install_checked() {
  local source="$1" destination="$2"
  if [[ -e "$destination" ]]; then
    if cmp -s "$source" "$destination"; then
      echo "UNCHANGED $destination"
      return
    fi
    echo "CONFLICT: $destination already exists with different content." >&2
    echo "Review it manually; this script will not overwrite it." >&2
    return 1
  fi
  if [[ "$DRY_RUN" == true ]]; then
    echo "WOULD COPY $source -> $destination"
  else
    mkdir -p "$(dirname "$destination")"
    install -m 0644 "$source" "$destination"
    echo "COPIED $destination"
  fi
}

install_checked "$REPO_ROOT/vendor/liteos/product/liteos_common.mk" \
  "$AOSP_ROOT/vendor/liteos/product/liteos_common.mk"
install_checked "$REPO_ROOT/vendor/liteos/products/liteos_x86_64.mk" \
  "$AOSP_ROOT/vendor/liteos/products/liteos_x86_64.mk"
install_checked "$REPO_ROOT/vendor/liteos/products/AndroidProducts.mk" \
  "$AOSP_ROOT/vendor/liteos/products/AndroidProducts.mk"

if [[ "$DRY_RUN" == true ]]; then
  echo "Dry run complete; no files were changed."
else
  echo "LITEOS overlay copied. Next: source build/envsetup.sh && lunch liteos_x86_64-userdebug"
fi
