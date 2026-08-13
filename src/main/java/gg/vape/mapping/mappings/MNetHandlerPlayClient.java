package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.wrapper.impl.ForgeVersion;

public class MNetHandlerPlayClient
extends Mapping {
    private final MappingMethod handleEntityTeleportMethod;

    public static void handleEntityTeleport(MNetHandlerPlayClient mapping, Object packetListenerHandle, Object teleportPacketHandle) {
        mapping.invokeHandleEntityTeleport(packetListenerHandle, teleportPacketHandle);
    }

    public MNetHandlerPlayClient() {
        super(MappedClasses.Df);
        Class[] parameterTypes = new Class[]{MappedClasses.s};
        Class<Void> returnType = Void.TYPE;
        String methodName = "handleEntityTeleport";
        MNetHandlerPlayClient mapping = this;
        this.handleEntityTeleportMethod = ((MappingMethodBuilder)((MappingMethodBuilder)mapping.methodBuilder(methodName, returnType, parameterTypes).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "handleTeleportEntity")).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.z2)).buildMethod();
    }

    private void invokeHandleEntityTeleport(Object packetListenerHandle, Object teleportPacketHandle) {
        this.handleEntityTeleportMethod.invokeVoid(packetListenerHandle, teleportPacketHandle);
    }
}

