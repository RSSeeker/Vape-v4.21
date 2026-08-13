package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTessellator;
import gg.vape.wrapper.Wrapper;

public class Tessellator
extends Wrapper {
    public Tessellator(Object object) {
        super(object);
    }

    public void u(int n, int n2, int n3, int n4) {
        Tessellator.vapeInstance.getMappingsMapperCompat().RB.w(this.I, n, n2, n3, n4);
    }

    public void W(double d, double d2, double d3, double d4, double d5) {
        MTessellator.E(Tessellator.vapeInstance.getMappingsMapperCompat().RB, this.I, d, d2, d3, d4, d5);
    }


    public void M(int n) {
        MTessellator.k(Tessellator.vapeInstance.getMappingsMapperCompat().RB, this.I, n);
    }

    public void draw() {
        MTessellator.Y(Tessellator.vapeInstance.getMappingsMapperCompat().RB, this.I);
    }

    public boolean w() {
        return Tessellator.vapeInstance.getMappingsMapperCompat().RB.K(this.I);
    }

    public static Tessellator getInstance() {
        return new Tessellator(MTessellator.a(Tessellator.vapeInstance.getMappingsMapperCompat().RB));
    }

    public void X(double d, double d2, double d3) {
        MTessellator.v(Tessellator.vapeInstance.getMappingsMapperCompat().RB, this.I, d, d2, d3);
    }

    public WorldRenderer getWorldRenderer() {
        return new WorldRenderer(MTessellator.W(Tessellator.vapeInstance.getMappingsMapperCompat().RB, this.I));
    }

    public void h() {
        if (ForgeVersion.MC_1_21_0.d()) {
            return;
        }
        MTessellator.z(Tessellator.vapeInstance.getMappingsMapperCompat().RB, this.I);
    }
}

