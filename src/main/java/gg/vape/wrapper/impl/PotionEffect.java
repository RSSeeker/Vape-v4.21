package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MPotionEffect;
import gg.vape.wrapper.Wrapper;

public class PotionEffect
extends Wrapper {
    public Holder t() {
        return new Holder(MPotionEffect.I$src$Ljava_lang_Object_$1dcpybi(PotionEffect.vapeInstance.getMappings().C8, this.I));
    }

    public int L() {
        return MPotionEffect.K(PotionEffect.vapeInstance.getMappings().C8, this.I);
    }

    public boolean A() {
        return MPotionEffect.e$src$Z$v3xxtq(PotionEffect.vapeInstance.getMappings().C8, this.I);
    }

    private static PotionEffect e(StatusEffect statusEffect, int n, int n2) {
        return new PotionEffect(PotionEffect.vapeInstance.getMappings().C8.A(statusEffect.getObject(), n, n2));
    }

    public static PotionEffect o(int n, int n2, int n3) {
        if (ForgeVersion.MC_1_20_6.d()) {
            Registry registry = BuiltInRegistries.j();
            Holder holder = registry.J(registry.t(n));
            return PotionEffect.m(holder, n2, n3);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return PotionEffect.e(StatusEffect.E(n), n2, n3);
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return PotionEffect.v(Potion.getPotionById(n), n2, n3);
        }
        return new PotionEffect(MPotionEffect.q(PotionEffect.vapeInstance.getMappings().C8, n, n2, n3));
    }

    private static PotionEffect m(Holder holder, int n, int n2) {
        return new PotionEffect(PotionEffect.vapeInstance.getMappings().C8.A(holder.getObject(), n, n2));
    }

    public PotionEffect(Object object) {
        super(object);
    }

    public StatusEffect i() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return new StatusEffect(new Holder(MPotionEffect.I$src$Ljava_lang_Object_$1dcpybi(PotionEffect.vapeInstance.getMappings().C8, this.I)).N());
        }
        return new StatusEffect(MPotionEffect.I$src$Ljava_lang_Object_$1dcpybi(PotionEffect.vapeInstance.getMappings().C8, this.I));
    }

    public int k() {
        return MPotionEffect.e(PotionEffect.vapeInstance.getMappings().C8, this.I);
    }


    private static PotionEffect v(Potion potion, int n, int n2) {
        return new PotionEffect(PotionEffect.vapeInstance.getMappings().C8.A(potion.getObject(), n, n2));
    }

    public int C() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return StatusEffect.v(this.i());
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return new Potion(MPotionEffect.I$src$Ljava_lang_Object_$1dcpybi(PotionEffect.vapeInstance.getMappings().C8, this.I)).getId();
        }
        return MPotionEffect.I(PotionEffect.vapeInstance.getMappings().C8, this.I);
    }
}

