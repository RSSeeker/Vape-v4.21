package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MCPacketPlayerBlockPlacement
extends Mapping {
    private MappingField facingXField;
    private MappingField sequenceField;
    private MappingField facingZField;
    private MappingField itemStackField;
    private MappingField blockHitField;
    private MappingField placedBlockDirectionField;
    private MappingField handField;
    private MappingField blockPositionField;
    private MappingField facingYField;

    public float getFacingX(Object packet) {
        return this.facingXField.getFloat(packet);
    }

    public Object getHand(Object packet) {
        return this.handField.getObject(packet);
    }

    public MCPacketPlayerBlockPlacement() {
        this(MPacketIdFactory.getPacketMappingControlFlowState());
    }

    private MCPacketPlayerBlockPlacement(GuiComponent[] controlFlowState) {
        super(MappedClasses.YB);
        if (ForgeVersion.MC_1_16_5_ACTUAL.v()) {
            if (ForgeVersion.MC_1_7_10.L() && !Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
                this.itemStackField = this.J("field_149580_e", Wrapper.isNativeAvailable, MappedClasses.VK);
            } else if (!ForgeVersion.MC_1_12_2.d()) {
                this.itemStackField = this.J("stack", true, MappedClasses.VK);
            }
            if (ForgeVersion.MC_1_7_10.Y()) {
                this.blockPositionField = this.J("position", true, MappedClasses.lf);
            }
            this.placedBlockDirectionField = this.J("placedBlockDirection", true, Integer.TYPE);
            this.facingXField = this.J("facingX", true, Float.TYPE);
            this.facingYField = this.J("facingY", true, Float.TYPE);
            this.facingZField = this.J("facingZ", true, Float.TYPE);
        } else {
            this.blockHitField = this.J("blockHit", true, MappedClasses.qF);
            this.handField = this.J("hand", true, MappedClasses.Yf);
            if (ForgeVersion.MC_1_21_11.d()) {
                this.sequenceField = this.J("sequence", true, Integer.TYPE);
            }
        }
    }

    public int getSequence(Object packet) {
        return this.sequenceField.getInt(packet);
    }

    public Object getBlockHit(Object packet) {
        return this.blockHitField.getObject(packet);
    }

    public float getFacingZ(Object packet) {
        return this.facingZField.getFloat(packet);
    }

    public float getFacingY(Object packet) {
        return this.facingYField.getFloat(packet);
    }

    public Object getItemStack(Object packet) {
        return this.itemStackField.getObject(packet);
    }

    public int getPlacedBlockDirection(Object packet) {
        return this.placedBlockDirectionField.getInt(packet);
    }

    public Object getBlockPosition(Object packet) {
        return this.blockPositionField.getObject(packet);
    }
}

