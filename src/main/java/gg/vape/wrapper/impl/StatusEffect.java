package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MStatusEffect;
import gg.vape.wrapper.Wrapper;

public class StatusEffect
extends Wrapper {
    public boolean p() {
        Object object = this.I;
        if (this.isInstance(MappedClasses.Vo)) {
            object = new Holder(this.I).N();
        }
        return MStatusEffect.r(StatusEffect.vapeInstance.getMappingsMapperCompat().Rb, object);
    }

    public StatusEffect(Object object) {
        super(object);
    }

    public static int v(StatusEffect statusEffect) {
        if (ForgeVersion.MC_1_20_6.d()) {
            Registry registry = BuiltInRegistries.j();
            StatusEffect statusEffect2 = statusEffect;
            if (statusEffect.isInstance(MappedClasses.Vo)) {
                statusEffect2 = new StatusEffect(new Holder(statusEffect.getObject()).N());
            }
            return registry.K(statusEffect2.getObject());
        }
        return MStatusEffect.a(StatusEffect.vapeInstance.getMappingsMapperCompat().Rb, statusEffect.getObject());
    }

    public static StatusEffect V() {
        return StatusEffect.E(9);
    }

    public String d() {
        Object object = this.I;
        if (this.isInstance(MappedClasses.Vo)) {
            object = new Holder(this.I).N();
        }
        ITextComponent iTextComponent = new ITextComponent(MStatusEffect.g(StatusEffect.vapeInstance.getMappingsMapperCompat().Rb, object));
        return iTextComponent.getFormattedText();
    }

    public static StatusEffect E(int n) {
        if (ForgeVersion.MC_1_20_6.d()) {
            Registry registry = BuiltInRegistries.j();
            return new StatusEffect(registry.t(n));
        }
        return new StatusEffect(StatusEffect.vapeInstance.getMappingsMapperCompat().Rb.O(n));
    }
}
