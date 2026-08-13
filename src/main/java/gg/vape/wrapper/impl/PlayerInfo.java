package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MPlayerInfo;
import gg.vape.wrapper.Wrapper;

public class PlayerInfo
extends Wrapper {

    public ITextComponent R() {
        return new ITextComponent(MPlayerInfo.l(PlayerInfo.vapeInstance.getMappingsMapperCompat().CG, this.getObject()));
    }

    public ScorePlayerTeam X() {
        return new ScorePlayerTeam(MPlayerInfo.Z(PlayerInfo.vapeInstance.getMappingsMapperCompat().CG, this.getObject()));
    }

    public ResourceLocation i() {
        Object object = MPlayerInfo.J(PlayerInfo.vapeInstance.getMappingsMapperCompat().CG, this.I);
        if (object == null) {
            return null;
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            return new PlayerSkin(object).getTexture();
        }
        return new ResourceLocation(object);
    }

    public GameProfile v() {
        return new GameProfile(MPlayerInfo.W(PlayerInfo.vapeInstance.getMappingsMapperCompat().CG, this.I));
    }

    public PlayerInfo(Object object) {
        super(object);
    }

    public int z() {
        return MPlayerInfo.H(PlayerInfo.vapeInstance.getMappingsMapperCompat().CG, this.I);
    }
}

