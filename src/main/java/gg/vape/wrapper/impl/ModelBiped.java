package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MModelBiped;

public class ModelBiped
extends ModelBipedSkeletonBridge {
    public ModelBiped(Object object) {
        super(object);
    }

    public ModelRenderer getBipedLeftArm() {
        return new ModelRenderer(MModelBiped.j(ModelBiped.vapeInstance.getMappingsMapperCompat().Ct, this.I));
    }

    public ModelRenderer getBipedLeftLeg() {
        return new ModelRenderer(MModelBiped.k(ModelBiped.vapeInstance.getMappingsMapperCompat().Ct, this.I));
    }

    public ModelRenderer getBipedRightLeg() {
        return new ModelRenderer(MModelBiped.J(ModelBiped.vapeInstance.getMappingsMapperCompat().Ct, this.I));
    }

    public ModelRenderer getBipedRightArm() {
        return new ModelRenderer(MModelBiped.R(ModelBiped.vapeInstance.getMappingsMapperCompat().Ct, this.I));
    }

    public ModelRenderer Z() {
        return new ModelRenderer(MModelBiped.a(ModelBiped.vapeInstance.getMappingsMapperCompat().Ct, this.I));
    }

    public ModelRenderer F() {
        return new ModelRenderer(MModelBiped.x(ModelBiped.vapeInstance.getMappingsMapperCompat().Ct, this.I));
    }
}

