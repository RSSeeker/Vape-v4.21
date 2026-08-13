package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MNetworkManager;
import gg.vape.wrapper.impl.ForgeVersion;

public class MPacket
extends Mapping {
    private final MappingMethod processPacketMethod;
    private MappingMethod hasPriorityMethod;

    private void invokeProcessPacket(Object packetHandle, Object listenerHandle) {
        this.processPacketMethod.invokeVoid(packetHandle, listenerHandle);
    }

    public static boolean hasPriority(MPacket mapping, Object packetHandle) {
        return mapping.invokeHasPriority(packetHandle);
    }

    public static void processPacket(MPacket mapping, Object packetHandle, Object listenerHandle) {
        mapping.invokeProcessPacket(packetHandle, listenerHandle);
    }

    public MPacket() {
        this(MNetworkManager.Q());
    }

    private MPacket(String[] controlFlowState) {
        super(MappedClasses.Fm);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_7_10.L()) {
                Class[] hasPriorityParameterTypes = new Class[]{};
                Class<Boolean> hasPriorityReturnType = Boolean.TYPE;
                boolean hasPriorityPublic = true;
                String hasPriorityMethodName = "hasPriority";
                MPacket mapping = this;
                this.hasPriorityMethod = mapping.Y(hasPriorityMethodName, hasPriorityPublic, hasPriorityReturnType, hasPriorityParameterTypes);
            }
            Class[] processPacketParameterTypes = new Class[]{MappedClasses.Yy};
            Class<Void> processPacketReturnType = Void.TYPE;
            boolean processPacketPublic = true;
            String processPacketMethodName = "processPacket";
            MPacket processPacketMapping = this;
            this.processPacketMethod = processPacketMapping.Y(processPacketMethodName, processPacketPublic, processPacketReturnType, processPacketParameterTypes);
            return;
        }
        this.processPacketMethod = null;
    }


    private boolean invokeHasPriority(Object packetHandle) {
        return this.hasPriorityMethod.invokeBoolean(packetHandle, new Object[0]);
    }
}
