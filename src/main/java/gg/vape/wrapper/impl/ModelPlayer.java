package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MModelPlayer;
import gg.vape.wrapper.Wrapper;

public class ModelPlayer
extends Wrapper {
    public boolean isFlying() {
        return MModelPlayer.v(ModelPlayer.vapeInstance.getMappings().Ri, this.I);
    }

    public boolean N() {
        return MModelPlayer.u(ModelPlayer.vapeInstance.getMappings().Ri, this.I);
    }

    public float m$src$F$1kykyr0() {
        return MModelPlayer.p(ModelPlayer.vapeInstance.getMappings().Ri, this.I);
    }

    public boolean isCreativeMode() {
        return MModelPlayer.k(ModelPlayer.vapeInstance.getMappings().Ri, this.I);
    }

    public float l() {
        return MModelPlayer.Y(ModelPlayer.vapeInstance.getMappings().Ri, this.I);
    }

    public boolean H() {
        return MModelPlayer.D(ModelPlayer.vapeInstance.getMappings().Ri, this.I);
    }

    public void c(boolean bl) {
        MModelPlayer.t(ModelPlayer.vapeInstance.getMappings().Ri, this.I, bl);
    }

    public ModelPlayer(Object object) {
        super(object);
    }

    public boolean c() {
        return MModelPlayer.a(ModelPlayer.vapeInstance.getMappings().Ri, this.I);
    }

    public static ModelPlayer Q() {
        return new ModelPlayer(MModelPlayer.C(ModelPlayer.vapeInstance.getMappings().Ri));
    }

    public void G(float f) {
        MModelPlayer.H(ModelPlayer.vapeInstance.getMappings().Ri, this.I, f);
    }

    public void n(boolean bl) {
        MModelPlayer.y(ModelPlayer.vapeInstance.getMappings().Ri, this.I, bl);
    }

    public void R(float f) {
        MModelPlayer.E(ModelPlayer.vapeInstance.getMappings().Ri, this.I, f);
    }
}

