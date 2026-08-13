package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MEnchantmentHelperBridge
extends Mapping {
    private final MappingMethod createLookupMethod;
    private static final String CREATE_LOOKUP_METHOD_NAME;
    private static int vanillaRegistriesControlFlowState;

    public MEnchantmentHelperBridge() {
        this(MEnchantmentHelperBridge.getVanillaRegistriesControlFlowState());
    }

    private MEnchantmentHelperBridge(int controlFlowState) {
        super(MappedClasses.qV);
        int currentControlFlowState = controlFlowState;
        Class[] parameterTypes = new Class[]{};
        Class returnType = MappedClasses.zi;
        boolean methodPublic = true;
        String methodName = CREATE_LOOKUP_METHOD_NAME;
        MEnchantmentHelperBridge mapping = this;
        this.createLookupMethod = mapping.registerStaticMethod(methodName, methodPublic, returnType, parameterTypes);
    }

    public static int getControlFlowConstant() {
        int controlFlowState = MEnchantmentHelperBridge.getVanillaRegistriesControlFlowState();
        return 41;
    }

    public Object createLookup() {
        return this.createLookupMethod.invokeObject(null, new Object[0]);
    }

    public static void setVanillaRegistriesControlFlowState(int state) {
        vanillaRegistriesControlFlowState = state;
    }

    static {
        MEnchantmentHelperBridge.setVanillaRegistriesControlFlowState(0);
        CREATE_LOOKUP_METHOD_NAME = "createLookup";
    }

    public static int getVanillaRegistriesControlFlowState() {
        return vanillaRegistriesControlFlowState;
    }

}

