package gg.vape.wrapper.impl;

public class PlayerModel
extends ModelBiped {
    public PlayerModel(Object object) {
        super(object);
    }

    public ModelRenderer l() {
        return new ModelRenderer(PlayerModel.vapeInstance.getMappings().Rc.Y(this.I));
    }

    public ModelRenderer u() {
        return new ModelRenderer(PlayerModel.vapeInstance.getMappings().Rc.G(this.I));
    }

    public ModelRenderer m$src$Lgg_vape_wrapper_impl_ModelRenderer_$1hr0p8w() {
        return new ModelRenderer(PlayerModel.vapeInstance.getMappings().Rc.J(this.I));
    }

    public ModelRenderer e() {
        return new ModelRenderer(PlayerModel.vapeInstance.getMappings().Rc.e(this.I));
    }

    public ModelRenderer s() {
        return new ModelRenderer(PlayerModel.vapeInstance.getMappings().Rc.F(this.I));
    }
}

