package gg.vape.utils.render;

public enum GpuVendor {
    NVIDIA,
    AMD,
    INTEL,
    UNKNOWN;

    private static final /* synthetic */ GpuVendor[] VALUES;

    static {
        String[] legacyEnumNames = new String[]{"UNKNOWN", "NVIDIA", "AMD", "INTEL"};




        VALUES = new GpuVendor[]{NVIDIA, AMD, INTEL, UNKNOWN};
    }
}
