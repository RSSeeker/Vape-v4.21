package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MShortPacketFieldBridge
extends Mapping {
    private final MappingField transactionIdField;

    public short getTransactionId(Object packet) {
        return this.transactionIdField.getShort(packet);
    }

    public MShortPacketFieldBridge() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MShortPacketFieldBridge(GuiComponent[] controlFlowState) {
        super(MappedClasses.zy);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_7_10.L() && !Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
                this.transactionIdField = this.J("field_149534_b", Wrapper.isNativeAvailable, Short.TYPE);
            } else {
                this.transactionIdField = this.J("uid", true, Short.TYPE);
            }
            return;
        }
        if (!ForgeVersion.MC_1_7_10.L()) {
            this.J("field_149534_b", Wrapper.isNativeAvailable, Short.TYPE);
        }
        this.transactionIdField = this.J("uid", true, Short.TYPE);
    }
}
