package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MRenderTypeBufferBridge
extends Mapping {
    private static String controlFlowMarker;
    private static final String ON_INPUT_RECEIVED_METHOD_NAME;
    private final MappingMethod onInputReceivedMethod;

    public MRenderTypeBufferBridge() {
        this(MRenderTypeBufferBridge.getControlFlowMarker());
    }

    private MRenderTypeBufferBridge(String marker) {
        super(MappedClasses.lC);
        Class[] parameterTypes = new Class[]{};
        Class<Void> returnType = Void.TYPE;
        boolean methodPublic = true;
        String methodName = ON_INPUT_RECEIVED_METHOD_NAME;
        MRenderTypeBufferBridge mapping = this;
        this.onInputReceivedMethod = mapping.Y(methodName, methodPublic, returnType, parameterTypes);
        String currentControlFlowMarker = marker;
    }

    static {
        MRenderTypeBufferBridge.setControlFlowMarker("bZI7xb");
        ON_INPUT_RECEIVED_METHOD_NAME = "onInputReceived";
    }

    public static void onInputReceived(MRenderTypeBufferBridge mapping, Object trackerHandle) {
        mapping.onInputReceivedMethod.invokeVoidNoArgs(trackerHandle);
    }


    public static String getControlFlowMarker() {
        return controlFlowMarker;
    }

    public static void setControlFlowMarker(String marker) {
        controlFlowMarker = marker;
    }
}

