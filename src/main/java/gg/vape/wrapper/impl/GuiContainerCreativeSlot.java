package gg.vape.wrapper.impl;

public class GuiContainerCreativeSlot
extends Slot {
    public Slot getWrappedSlot() {
        Object slotHandle = GuiContainerCreativeSlot.vapeInstance.getMappingsMapperCompat().guiContainerCreativeSlot
                .getWrappedSlot(this.I);
        return new Slot(slotHandle);
    }

    public GuiContainerCreativeSlot(Object creativeSlotHandle) {
        super(creativeSlotHandle);
    }
}
