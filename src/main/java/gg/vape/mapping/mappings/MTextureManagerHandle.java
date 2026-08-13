package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MTextureManagerHandle
extends Mapping {
    private final MappingMethod getDepthTextureMethod;
    private final MappingMethod getColorTextureMethod;
    private static boolean renderTargetControlFlowState;


    public static boolean getRenderTargetControlFlowState() {
        return renderTargetControlFlowState;
    }

    public Object getDepthTexture(Object renderTarget) {
        return this.getDepthTextureMethod.invokeObject(renderTarget, new Object[0]);
    }

    public static void setRenderTargetControlFlowState(boolean state) {
        renderTargetControlFlowState = state;
    }

    static {
        MTextureManagerHandle.setRenderTargetControlFlowState(true);
    }

    public static boolean getDisabledControlFlowState() {
        boolean bl = MTextureManagerHandle.getRenderTargetControlFlowState();
        return false;
    }

    public Object getColorTexture(Object renderTarget) {
        return this.getColorTextureMethod.invokeObject(renderTarget, new Object[0]);
    }

    public MTextureManagerHandle() {
        this(MTextureManagerHandle.getRenderTargetControlFlowState());
    }

    private MTextureManagerHandle(boolean bl) {
        super(MappedClasses.DA);
        Class[] classArray = new Class[]{};
        Class clazz = MappedClasses.GPU_TEXTURE;
        boolean bl2 = true;
        String string = "getColorTexture";
        MTextureManagerHandle mTextureManagerHandle = this;
        this.getColorTextureMethod = this.Y(string, bl2, clazz, classArray);
        boolean bl3 = bl;
        Class[] classArray2 = new Class[]{};
        Class clazz2 = MappedClasses.GPU_TEXTURE;
        boolean bl4 = true;
        String string2 = "getDepthTexture";
        MTextureManagerHandle mTextureManagerHandle2 = this;
        this.getDepthTextureMethod = this.Y(string2, bl4, clazz2, classArray2);
    }
}

