package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class PlayerSkin
extends Wrapper {

    public TextureAtlasSpriteInfo getBodySpriteInfo() {
        return new TextureAtlasSpriteInfo(PlayerSkin.vapeInstance.getMappingsMapperCompat().playerSkin.getBody(this.I));
    }

    public PlayerSkin(Object playerSkinHandle) {
        super(playerSkinHandle);
    }

    public ResourceLocation getTexture() {
        if (ForgeVersion.MC_1_21_10.d()) {
            return this.getBodySpriteInfo().getTexturePath();
        }
        return new ResourceLocation(PlayerSkin.vapeInstance.getMappingsMapperCompat().playerSkin.getTexture(this.I));
    }
}

