package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityArrow;

public class EntityArrow
extends Entity {

    public double o() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return this.P$src$D$xovcst();
        }
        return MEntityArrow.g(EntityArrow.vapeInstance.getMappings().qo, this.I);
    }

    public double L() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return this.P$src$D$xovcst();
        }
        return MEntityArrow.d(EntityArrow.vapeInstance.getMappings().qo, this.I);
    }

    public double P$src$D$xovcst() {
        return MEntityArrow.t(EntityArrow.vapeInstance.getMappings().qo, this.I);
    }

    public EntityArrow(Object object) {
        super(object);
    }

    public double X$src$D$xt9pjp() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return this.P$src$D$xovcst();
        }
        return MEntityArrow.D(EntityArrow.vapeInstance.getMappings().qo, this.I);
    }
}

