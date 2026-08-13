package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSPacketAnimation
extends Mapping {
    private MappingField animationTypeField;
    private MappingField entityIdField;

    public int getEntityId(Object packet) {
        return this.entityIdField.getInt(packet);
    }

    public MSPacketAnimation() {
        this(MSPacketEntityVelocity.getPacketMappingControlFlowState());
    }

    private MSPacketAnimation(int[] controlFlowState) {
        super(MappedClasses.ZQ);
        if (controlFlowState != null) {
            if (ForgeVersion.MC_1_7_10.L() && !Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
                this.entityIdField = this.J("field_148981_a", Wrapper.isNativeAvailable, Integer.TYPE);
                this.animationTypeField = this.J("field_148980_b", Wrapper.isNativeAvailable, Integer.TYPE);
            } else {
                this.entityIdField = this.J("entityId", true, Integer.TYPE);
                this.animationTypeField = this.J("type", true, Integer.TYPE);
            }
            return;
        }
        this.animationTypeField = this.J("type", true, Integer.TYPE);
    }

    public int getAnimationType(Object packet) {
        return this.animationTypeField.getInt(packet);
    }
}

