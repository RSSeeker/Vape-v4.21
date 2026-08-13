package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MHolder;
import gg.vape.wrapper.Wrapper;

import java.util.Optional;

public class Holder
extends Wrapper {
    public boolean F(ResourceKey jy_12) {
        return MHolder.q(Holder.vapeInstance.getMappingsMapperCompat().hN, this.I, jy_12.getObject());
    }

    public String Z() {
        return MHolder.F(Holder.vapeInstance.getMappingsMapperCompat().hN, this.I);
    }

    public static Holder A(Object object) {
        return new Holder(MHolder.f(Holder.vapeInstance.getMappingsMapperCompat().hN, object));
    }

    public Holder(Object object) {
        super(object);
    }

    public Object N() {
        return MHolder.E(Holder.vapeInstance.getMappingsMapperCompat().hN, this.I);
    }

    public Optional f() {
        return (Optional)MHolder.m(Holder.vapeInstance.getMappingsMapperCompat().hN, this.I);
    }
}

