package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.config.ClientSettings;
import gg.vape.wrapper.Wrapper;

public class KeyBinding
extends Wrapper {

    public void setPressed(boolean bl) {
        if (bl) {
            this.I();
        } else {
            this.e();
        }
    }

    public static void H() {
        Vape.INSTANCE.getMappings().hJ.R();
    }

    public void Z() {
        boolean bl;
        boolean bl2 = ClientSettings.isPhysicalKeyDown(this);
        boolean bl3 = bl = this.isKeyDown() != bl2;
        if (bl) {
            this.setPressed(bl2);
        }
    }

    public boolean u() {
        return KeyBinding.vapeInstance.getMappings().hJ.a(this.I);
    }

    public void e() {
        if (ForgeVersion.MC_1_16_5.d()) {
            KeyBinding.vapeInstance.getMappings().hJ.S(this.I);
            return;
        }
        this.onTick(0);
        KeyBinding.setKeyBindState(this, false);
    }

    public boolean isKeyDown() {
        return KeyBinding.vapeInstance.getMappings().hJ.S$src$Z$wvm5qt(this.I);
    }

    public static void setKeyBindState(KeyBinding keyBinding, boolean bl) {
        if (ForgeVersion.MC_1_16_5.d()) {
            KeyBinding.vapeInstance.getMappings().hJ.d(keyBinding.u$src$Lgg_vape_wrapper_impl_InputMappingsInput_$1c10gpv().getObject(), bl);
            return;
        }
        KeyBinding.vapeInstance.getMappings().hJ.g(keyBinding.getKeyCode(), bl);
    }

    public boolean isPressed() {
        return KeyBinding.vapeInstance.getMappings().hJ.M(this.I);
    }

    public void onTick(int n) {
        KeyBinding.vapeInstance.getMappings().hJ.q(this.I, n);
    }

    public KeyBinding(Object object) {
        super(object);
    }

    public int getKeyCode() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.u$src$Lgg_vape_wrapper_impl_InputMappingsInput_$1c10gpv().getKeyCode();
        }
        return KeyBinding.vapeInstance.getMappings().hJ.B(this.I);
    }

    private InputMappingsInput u$src$Lgg_vape_wrapper_impl_InputMappingsInput_$1c10gpv() {
        return new InputMappingsInput(KeyBinding.vapeInstance.getMappings().hJ.l(this.I));
    }

    public static void onTick(KeyBinding keyBinding) {
        if (ForgeVersion.MC_1_16_5.d()) {
            KeyBinding.vapeInstance.getMappings().hJ.x(keyBinding.u$src$Lgg_vape_wrapper_impl_InputMappingsInput_$1c10gpv().getObject());
            return;
        }
        KeyBinding.vapeInstance.getMappings().hJ.s(keyBinding.getKeyCode());
    }

    public void setCancelled(boolean bl) {
        KeyBinding.vapeInstance.getMappings().hJ.o(this.I, bl);
    }

    public void I() {
        KeyBinding.setKeyBindState(this, true);
        KeyBinding.onTick(this);
    }

    public int V() {
        return KeyBinding.vapeInstance.getMappings().hJ.n(this.I);
    }
}

