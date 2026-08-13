package gg.vape.mapping.runtime;

import gg.vape.mapping.runtime.RuntimeNameMappingRegistry;
import java.util.HashMap;

public class ClassNameRemapTable {
    public static boolean propagateMappingsToRuntimeRegistry = false;
    private final HashMap<String, String> classNameMappings = new HashMap();
    private static int[] controlFlowState;

    public static int[] getControlFlowState() {
        return controlFlowState;
    }

    /*
     * WARNING - void declaration
     */
    public void Q(String sourceClassName, String targetClassName) {
        String previousTarget = this.classNameMappings.get(sourceClassName);
        this.classNameMappings.put(sourceClassName, targetClassName);
        if (propagateMappingsToRuntimeRegistry) {
            String remappedSourceName = RuntimeNameMappingRegistry.remapClassName(sourceClassName);
            String runtimeSourceName = remappedSourceName != null ? remappedSourceName : sourceClassName;
            RuntimeNameMappingRegistry.registerClassName(runtimeSourceName, targetClassName);
        }
    }

    public static void setControlFlowState(int[] state) {
        controlFlowState = state;
    }

    public String lookupRemappedClassName(String sourceClassName) {
        return this.classNameMappings.getOrDefault(sourceClassName, null);
    }


    static {
        ClassNameRemapTable.setControlFlowState(new int[3]);
    }
}
