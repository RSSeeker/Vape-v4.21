package gg.vape.wrapper.impl;

public class RenderPlayer
extends RenderLivingBase<EntityPlayer> {
    public RenderPlayer(Object renderPlayerHandle) {
        super(renderPlayerHandle);
    }

    public ModelBiped getModelBipedMain() {
        return new ModelBiped(RenderPlayer.vapeInstance.getMappingsMapperCompat().renderPlayer.getMainModel(this.I));
    }

    @Override
    public PlayerModel getMainModel() {
        return new PlayerModel(RenderPlayer.vapeInstance.getMappingsMapperCompat().renderPlayer.getMainModel(this.I));
    }
}
