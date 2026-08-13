package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MItem_ToolMaterial;
import gg.vape.wrapper.Wrapper;

public class ToolMaterial
extends Wrapper {
    public float I() {
        return MItem_ToolMaterial.j(ToolMaterial.vapeInstance.getMappings().Re, this.I);
    }

    public static ToolMaterial S() {
        return new ToolMaterial(MItem_ToolMaterial.H(ToolMaterial.vapeInstance.getMappings().Re));
    }

    public static ToolMaterial u() {
        return new ToolMaterial(MItem_ToolMaterial.r(ToolMaterial.vapeInstance.getMappings().Re));
    }

    public ToolMaterial(Object object) {
        super(object);
    }

    public static ToolMaterial f() {
        return new ToolMaterial(MItem_ToolMaterial.U(ToolMaterial.vapeInstance.getMappings().Re));
    }

    public static ToolMaterial I$src$Lgg_vape_wrapper_impl_ToolMaterial_$3t5lsk() {
        return new ToolMaterial(MItem_ToolMaterial.M(ToolMaterial.vapeInstance.getMappings().Re));
    }
}

