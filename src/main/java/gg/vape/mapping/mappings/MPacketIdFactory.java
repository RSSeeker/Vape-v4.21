package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MPacketIdFactory
extends Mapping {
    private final MappingMethod confirmTeleportPacketConstructor;
    private static GuiComponent[] packetMappingControlFlowState;

    public static GuiComponent[] getPacketMappingControlFlowState() {
        return packetMappingControlFlowState;
    }

    public static void setPacketMappingControlFlowState(GuiComponent[] state) {
        packetMappingControlFlowState = state;
    }

    static {
        if (MPacketIdFactory.getPacketMappingControlFlowState() == null) {
            MPacketIdFactory.setPacketMappingControlFlowState(new GuiComponent[2]);
        }
    }

    public Object createConfirmTeleportPacket(int teleportId) {
        return this.confirmTeleportPacketConstructor.newInstance(teleportId);
    }

    public MPacketIdFactory() {
        super(MappedClasses.Yq);
        this.confirmTeleportPacketConstructor = this.registerConstructor(new Class[]{Integer.TYPE});
    }
}

