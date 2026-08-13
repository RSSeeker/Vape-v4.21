package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MCPacketUseEntityPacket
extends Mapping {
    private MappingField actionField;
    private MappingField hitLocationField;
    private final MappingField entityIdField;
    private final MappingField nestedEntityIdField;

    public Object getAction(Object packet) {
        if (this.actionField == null) {
            return null;
        }
        return this.actionField.getObject(packet);
    }

    public MCPacketUseEntityPacket() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MCPacketUseEntityPacket(GuiComponent[] controlFlowState) {
        super(MappedClasses.Fa);
        if (ForgeVersion.MC_1_8_9.d()) {
            if (ForgeVersion.MC_1_17.d()) {
                if (ForgeVersion.MC_26_1.v()) {
                    this.actionField = this.J("action", true, MappedClasses.lw);
                }
            } else {
                this.actionField = this.J("action", true, MappedClasses.D5);
            }
            this.entityIdField = this.J("entityId", true, Integer.TYPE);
        } else if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
            this.actionField = this.J("action", true, MappedClasses.D5);
            this.entityIdField = this.J("entityId", true, Integer.TYPE);
        } else {
            this.actionField = this.J("field_149566_b", Wrapper.isNativeAvailable, MappedClasses.D5);
            this.entityIdField = this.J("field_149567_a", Wrapper.isNativeAvailable, Integer.TYPE);
        }
        if (ForgeVersion.MC_26_1.d()) {
            this.nestedEntityIdField = this.registerInstanceFieldForOwner(MappedClasses.ZW, "entityId", true, Integer.TYPE);
        } else {
            this.nestedEntityIdField = null;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_26_1.d()) {
                this.hitLocationField = this.registerInstanceFieldWithSecondaryFlag("location", true, true, MappedClasses.qP);
            } else if (ForgeVersion.MC_1_20_6.v()) {
                this.hitLocationField = this.J("location", true, MappedClasses.qP);
            }
        } else if (ForgeVersion.MC_1_8_9.d()) {
            this.hitLocationField = this.J("hitVec", true, MappedClasses.qP);
        }
    }

    public void setEntityId(Object packet, int entityId) {
        if (ForgeVersion.MC_26_1.d() && MappedClasses.ZW != null && MappedClasses.ZW.isInstance(packet)) {
            this.nestedEntityIdField.setInt(packet, entityId);
            return;
        }
        this.entityIdField.setInt(packet, entityId);
    }

    public Object getHitLocation(Object packet) {
        return this.hitLocationField.getObject(packet);
    }

    public int getEntityId(Object packet) {
        if (ForgeVersion.MC_26_1.d() && MappedClasses.ZW != null && MappedClasses.ZW.isInstance(packet)) {
            return this.nestedEntityIdField.getInt(packet);
        }
        return this.entityIdField.getInt(packet);
    }
}

