package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MFramebuffer;
import gg.vape.wrapper.Wrapper;

public class Framebuffer
extends Wrapper {
    public Framebuffer(Object object) {
        super(object);
    }

    public void createFramebuffer(int n, int n2) {
        if (ForgeVersion.MC_1_16_5.d()) {
            MFramebuffer.w(Framebuffer.vapeInstance.getMappings().R9, this.I, n, n2, false);
            return;
        }
        MFramebuffer.G(Framebuffer.vapeInstance.getMappings().R9, this.I, n, n2);
    }

    public void unbindFramebuffer() {
        MFramebuffer.m(Framebuffer.vapeInstance.getMappings().R9, this.I);
    }

    public void unbindFramebufferTexture() {
        MFramebuffer.D(Framebuffer.vapeInstance.getMappings().R9, this.I);
    }

    public void setDepthBuffer(int n) {
        MFramebuffer.depthBuffer(Framebuffer.vapeInstance.getMappings().R9, this.I, n);
    }

    public void x() {
        MFramebuffer.e(Framebuffer.vapeInstance.getMappings().R9, this.I);
    }

    public static Framebuffer create(int n, int n2, boolean bl) {
        if (ForgeVersion.MC_1_17.d()) {
            return new Framebuffer(MFramebuffer.create(Framebuffer.vapeInstance.getMappings().R9, n, n2, true));
        }
        return new Framebuffer(MFramebuffer.create(Framebuffer.vapeInstance.getMappings().R9, n, n2, bl));
    }

    public int getDepthBuffer() {
        return MFramebuffer.L(Framebuffer.vapeInstance.getMappings().R9, this.I);
    }

    public void createBindFramebuffer(int n, int n2) {
        MFramebuffer.f(Framebuffer.vapeInstance.getMappings().R9, this.I, n, n2);
    }


    public void bindFramebuffer(boolean bl) {
        MFramebuffer.bindFramebuffer(Framebuffer.vapeInstance.getMappings().R9, this.I, bl);
    }

    public void bindFramebufferTexture() {
        MFramebuffer.l(Framebuffer.vapeInstance.getMappings().R9, this.I);
    }
}

