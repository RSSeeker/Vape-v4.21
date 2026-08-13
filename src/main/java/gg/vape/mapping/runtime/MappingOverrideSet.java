package gg.vape.mapping.runtime;

import gg.vape.mapping.Mapping;
import java.util.HashMap;
import java.util.Map;

public abstract class MappingOverrideSet {
    private final int mappingVersion;
    private static String controlFlowMarker;
    private final Map<Class, Class> overrides = new HashMap<Class, Class>();

    public static String getControlFlowMarker() {
        return controlFlowMarker;
    }

    protected abstract void registerOverrides();

    public static void setControlFlowMarker(String marker) {
        controlFlowMarker = marker;
    }

    public <T extends Mapping, O extends T> void registerOverride(Class<T> mappingClass, Class<O> overrideClass) {
        this.overrides.put(mappingClass, overrideClass);
    }

    public MappingOverrideSet(int mappingVersion) {
        this.mappingVersion = mappingVersion;
    }

    public Class getOverrideClass(Class mappingClass) {
        return this.overrides.get(mappingClass);
    }

    static {
        if (MappingOverrideSet.getControlFlowMarker() == null) {
            MappingOverrideSet.setControlFlowMarker("idae5");
        }
    }
}
