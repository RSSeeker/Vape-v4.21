package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MGuiContainer;

public class GuiContainer
extends GuiScreen {
    public static ResourceLocation m$src$Lgg_vape_wrapper_impl_ResourceLocation_$1fc62cj() {
        return new ResourceLocation(GuiContainer.vapeInstance.getMappings().hK.v());
    }

    public Slot P() {
        return new Slot(MGuiContainer.y(GuiContainer.vapeInstance.getMappings().hK, this.I));
    }

    public int v() {
        return MGuiContainer.G(GuiContainer.vapeInstance.getMappings().hK, this.I);
    }

    public int p() {
        return MGuiContainer.B(GuiContainer.vapeInstance.getMappings().hK, this.I);
    }

    public int x() {
        return MGuiContainer.i(GuiContainer.vapeInstance.getMappings().hK, this.I);
    }

    public Slot getSlotAtPosition(int n, int n2) {
        return new Slot(MGuiContainer.a(GuiContainer.vapeInstance.getMappings().hK, this.I, n, n2));
    }

    public int b() {
        return MGuiContainer.e(GuiContainer.vapeInstance.getMappings().hK, this.I);
    }

    public GuiContainer(Object object) {
        super(object);
    }

    public Container getInventorySlots() {
        return new Container(GuiContainer.vapeInstance.getMappings().hK.y(this.I));
    }
}

