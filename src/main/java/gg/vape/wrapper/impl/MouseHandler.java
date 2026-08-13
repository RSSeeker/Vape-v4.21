package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MInputMappings;
import gg.vape.wrapper.Wrapper;

public class MouseHandler
extends Wrapper {
    public void O() {
        MInputMappings.F$src$V$13evi63(MouseHandler.vapeInstance.getMappings().CW, this.I);
    }

    public double R() {
        return MInputMappings.g(MouseHandler.vapeInstance.getMappings().CW, this.I);
    }

    public MouseHandler(Object object) {
        super(object);
    }

    public void L(long l, int n, boolean bl) {
        MInputMappings.d(MouseHandler.vapeInstance.getMappings().CW, this.I, l, n, bl);
    }

    public void u() {
        MInputMappings.V(MouseHandler.vapeInstance.getMappings().CW, this.I);
    }

    public int d() {
        return MInputMappings.T(MouseHandler.vapeInstance.getMappings().CW, this.I);
    }

    public int z() {
        return MInputMappings.F(MouseHandler.vapeInstance.getMappings().CW, this.I);
    }

    public boolean Z() {
        return MInputMappings.w(MouseHandler.vapeInstance.getMappings().CW, this.I);
    }

    public double b() {
        return MInputMappings.B(MouseHandler.vapeInstance.getMappings().CW, this.I);
    }
}

