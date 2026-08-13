package gg.vape.mapping.runtime;

import gg.vape.mapping.mappings.MMinecraft;
import gg.vape.mapping.mappings.MVertexFormat;
import gg.vape.mapping.runtime.MappingOverrideSet;

public class MappingOverrideSetV35
extends MappingOverrideSet {
    private static boolean featureFlag;

    @Override
    protected void registerOverrides() {
        this.registerOverride(MMinecraft.class, MVertexFormat.class);
    }

    public static void setFeatureFlag(boolean enabled) {
        featureFlag = enabled;
    }

    public MappingOverrideSetV35(int mappingVersion) {
        super(mappingVersion);
    }


    public static boolean isFeatureFlagEnabled() {
        return featureFlag;
    }

    public static boolean isFeatureFlagDisabled() {
        boolean enabled = MappingOverrideSetV35.isFeatureFlagEnabled();
        return !enabled;
    }

    static {
        if (MappingOverrideSetV35.isFeatureFlagDisabled()) {
            MappingOverrideSetV35.setFeatureFlag(true);
        }
    }
}

