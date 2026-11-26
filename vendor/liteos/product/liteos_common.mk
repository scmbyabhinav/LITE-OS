# Shared LITEOS product properties.
# This fragment is not a standalone product and must be inherited by a
# concrete AOSP product definition after the LITEOS overlay is integrated.

PRODUCT_PRODUCT_PROPERTIES += \
    ro.liteos.identity=liteos \
    ro.liteos.release.track=development \
    ro.liteos.profile.default=auto \
    ro.liteos.performance.target_ram_mb=2048 \
    ro.liteos.telemetry.enabled=false
