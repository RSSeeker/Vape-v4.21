package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MGuiContainerCreativeSlot
extends Mapping {
    private final MappingField wrappedSlotField;

    public MGuiContainerCreativeSlot() {
        this(MSlot.getSlotControlFlowState());
    }

    private MGuiContainerCreativeSlot(int[] slotMappingState) {
        super(MappedClasses.VG);
        if (slotMappingState != null) {
            this.wrappedSlotField = this.J("slot", true, MappedClasses.YQ);
            return;
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            this.wrappedSlotField = this.J("field_148332_b", Wrapper.isNativeAvailable, MappedClasses.YQ);
        } else {
            this.wrappedSlotField = this.J("slot", true, MappedClasses.YQ);
        }
    }

    public Object getWrappedSlot(Object creativeSlotHandle) {
        return this.wrappedSlotField.getObject(creativeSlotHandle);
    }

}
