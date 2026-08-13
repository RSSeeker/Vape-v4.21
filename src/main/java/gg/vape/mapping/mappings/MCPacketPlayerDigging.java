package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MCPacketPlayerDigging
extends Mapping {
    private MappingField legacyActionIdField;
    private MappingField positionField;
    private MappingField actionField;
    private MappingField facingField;

    public Object getPosition(Object packet) {
        return this.positionField.getObject(packet);
    }

    public Object getFacing(Object packet) {
        return this.facingField.getObject(packet);
    }

    public int getLegacyActionId(Object packet) {
        return this.legacyActionIdField.getInt(packet);
    }

    public Object getAction(Object packet) {
        return this.actionField.getObject(packet);
    }

    public MCPacketPlayerDigging() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MCPacketPlayerDigging(GuiComponent[] controlFlowState) {
        super(MappedClasses.DN);
        if (controlFlowState != null) {
            this.positionField = this.J("position", true, MappedClasses.lf);
            this.facingField = this.J("facing", true, MappedClasses.q0);
            if (ForgeVersion.MC_1_8_9.d()) {
                if (ForgeVersion.MC_1_8_9.L()) {
                    this.actionField = this.J("status", true, MappedClasses.FL);
                }
            } else {
                this.legacyActionIdField = this.J("field_149508_e", Wrapper.isNativeAvailable, Integer.TYPE);
            }
            return;
        }
        this.positionField = this.J("position", true, MappedClasses.lf);
        this.legacyActionIdField = this.J("facing", true, MappedClasses.q0);
    }
}

