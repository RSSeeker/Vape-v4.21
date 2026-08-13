package gg.vape.module.render.freecam;

import gg.vape.mapping.mappings.MFreecamPlayerBridge;
import gg.vape.wrapper.Wrapper;

public class FreecamPlayerBridge
extends Wrapper {
    public void setCloakZ(double z) {
        MFreecamPlayerBridge.A(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I, z);
    }

    public double getPreviousCloakZ() {
        return MFreecamPlayerBridge.Z(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I);
    }

    public void setPreviousCloakZ(double z) {
        MFreecamPlayerBridge.d(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I, z);
    }

    public double getPreviousCloakY() {
        return MFreecamPlayerBridge.m(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I);
    }

    public double getCloakY() {
        return MFreecamPlayerBridge.Y(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I);
    }

    public double getPreviousCloakX() {
        return MFreecamPlayerBridge.C(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I);
    }

    public void setCloakX(double x) {
        MFreecamPlayerBridge.c(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I, x);
    }

    public double getCloakX() {
        return MFreecamPlayerBridge.T(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I);
    }

    public void setPreviousCloakY(double y) {
        MFreecamPlayerBridge.E(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I, y);
    }

    public void setCloakY(double y) {
        MFreecamPlayerBridge.n(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I, y);
    }

    public void resetBobbingState() {
        MFreecamPlayerBridge.m$src$V$n8prsk(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I);
    }

    public void setPreviousCloakX(double x) {
        MFreecamPlayerBridge.y(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I, x);
    }

    public FreecamPlayerBridge(Object object) {
        super(object);
    }

    public double getCloakZ() {
        return MFreecamPlayerBridge.l(FreecamPlayerBridge.vapeInstance.getMappings().w, this.I);
    }
}
