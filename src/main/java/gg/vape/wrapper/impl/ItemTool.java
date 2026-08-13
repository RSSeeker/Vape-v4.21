package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MItemTool;

public class ItemTool
extends Item {
    public ItemTool(Object object) {
        super(object);
        if (ForgeVersion.MC_1_21_5.d()) {
            throw new UnsupportedOperationException("Removed");
        }
    }

    public float p$src$F$7elb4m() {
        if (ForgeVersion.MC_1_21_5.d()) {
            throw new UnsupportedOperationException("Removed");
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.u().I();
        }
        return MItemTool.T(ItemTool.vapeInstance.getMappings().M, this.I);
    }

    public ToolMaterial u() {
        if (ForgeVersion.MC_1_21_5.d()) {
            throw new UnsupportedOperationException("Removed");
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            return null;
        }
        return new ToolMaterial(MItemTool.R(ItemTool.vapeInstance.getMappings().M, this.I));
    }

    private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
        return unsupportedOperationException;
    }
}

