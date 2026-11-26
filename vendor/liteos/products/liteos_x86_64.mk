# LITEOS x86_64 emulator product.
# First integration target for AOSP product/configuration validation.
# This is an emulator target, not a physical-device ROM.

$(call inherit-product, $(SRC_TARGET_DIR)/product/sdk_phone_x86_64.mk)
$(call inherit-product, vendor/liteos/product/liteos_common.mk)

PRODUCT_NAME := liteos_x86_64
PRODUCT_BRAND := LITEOS
PRODUCT_MODEL := LITEOS x86_64 Emulator
PRODUCT_MANUFACTURER := LITEOS
