package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;

public class MFogRenderer
extends Mapping {
    public MappingMethod setupFogMethod;
    private MappingMethod getBufferMethod;

    public MFogRenderer() {
        this(MEntityRenderer.X());
    }

    private MFogRenderer(int initializationState) {
        super(MappedClasses.FOG_RENDERER);
        int unusedInitializationState = initializationState;
        if (ForgeVersion.MC_1_21_6.d()) {
            Class[] parameterTypes = new Class[]{MappedClasses.FOG_TYPE};
            Class returnType = MappedClasses.qk;
            boolean remap = true;
            String methodName = "getBuffer";
            MFogRenderer mappings = this;
            this.getBufferMethod = mappings.Y(methodName, remap, returnType, parameterTypes);
        }
        if (ForgeVersion.MC_1_21_10.d()) {
            this.setupFogMethod = null;
        } else if (Minecraft.isNativeAvailable) {
            if (ForgeVersion.MC_1_20_6.d()) {
                Class[] parameterTypes = new Class[]{MappedClasses.lt, MappedClasses.FOG_TYPE, Float.TYPE, Boolean.TYPE, Float.TYPE};
                Class<Void> returnType = Void.TYPE;
                boolean remap = true;
                String methodName = "setupFog";
                MFogRenderer mappings = this;
                this.setupFogMethod = mappings.registerStaticMethod(methodName, remap, returnType, parameterTypes);
            } else {
                Class[] parameterTypes = new Class[]{MappedClasses.lt, MappedClasses.FOG_TYPE, Float.TYPE, Boolean.TYPE};
                Class<Void> returnType = Void.TYPE;
                boolean remap = true;
                String methodName = "setupFog";
                MFogRenderer mappings = this;
                this.setupFogMethod = mappings.registerStaticMethod(methodName, remap, returnType, parameterTypes);
            }
        } else {
            Class[] parameterTypes = new Class[]{MappedClasses.lt, MappedClasses.FOG_TYPE, Float.TYPE, Boolean.TYPE, Float.TYPE};
            Class<Void> returnType = Void.TYPE;
            boolean remap = false;
            String methodName = "setupFog";
            MFogRenderer mappings = this;
            this.setupFogMethod = mappings.registerStaticMethod(methodName, remap, returnType, parameterTypes);
        }
    }

    public Object getBuffer(Object fogRenderer, Object fogType) {
        return this.getBufferMethod.invokeObject(fogRenderer, fogType);
    }
}
