#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PRODUCT_DIR="$ROOT/vendor/liteos/products"
PRODUCT_MK="$PRODUCT_DIR/liteos_x86_64.mk"
REGISTRY="$PRODUCT_DIR/AndroidProducts.mk"
COMMON="$ROOT/aosp/product/liteos_common.mk"

fail() {
  echo "AOSP product validation failed: $1" >&2
  exit 1
}

[[ -f "$PRODUCT_MK" ]] || fail "missing vendor/liteos/products/liteos_x86_64.mk"
[[ -f "$REGISTRY" ]] || fail "missing vendor/liteos/products/AndroidProducts.mk"
[[ -f "$COMMON" ]] || fail "missing aosp/product/liteos_common.mk"

grep -Fq 'sdk_phone_x86_64.mk' "$PRODUCT_MK" || fail "product must inherit the AOSP x86_64 emulator base"
grep -Fq 'vendor/liteos/product/liteos_common.mk' "$PRODUCT_MK" || fail "product must inherit shared LITEOS properties"
grep -Fq 'PRODUCT_NAME := liteos_x86_64' "$PRODUCT_MK" || fail "product name mismatch"
grep -Fq 'liteos_x86_64.mk' "$REGISTRY" || fail "product makefile is not registered"
grep -Fq 'liteos_x86_64-userdebug' "$REGISTRY" || fail "userdebug lunch choice is missing"
grep -Fq 'ro.liteos.identity=liteos' "$COMMON" || fail "LITEOS identity property is missing"

echo "AOSP product structure validation passed."
echo "Note: this checks repository structure only; it does not parse or build an AOSP tree."
