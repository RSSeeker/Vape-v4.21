package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MGameSettings;
import gg.vape.wrapper.Wrapper;

import java.util.List;

public class GameSettings
extends Wrapper {
    private static String[] L;

    public KeyBinding x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg() {
        return new KeyBinding(MGameSettings.E(GameSettings.vapeInstance.getMappings().RM, this.getObject()));
    }

    public boolean k() {
        if (ForgeVersion.MC_1_20_6.d()) {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.R(GameSettings.vapeInstance.getMappings().RM, this.I));
            return (Boolean)gameSettingsValue.i();
        }
        return MGameSettings.L(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    public void O(boolean bl) {
        if (ForgeVersion.MC_1_20_6.d()) {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.R(GameSettings.vapeInstance.getMappings().RM, this.I));
            gameSettingsValue.a(bl);
            return;
        }
        MGameSettings.z(GameSettings.vapeInstance.getMappings().RM, this.I, bl);
    }

    public void y(float f) {
        if (ForgeVersion.MC_1_20_6.d()) {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.H$src$Ljava_lang_Object_$1jqua4d(GameSettings.vapeInstance.getMappings().RM, this.I));
            gameSettingsValue.f(Double.valueOf((double)f));
            return;
        }
        MGameSettings.U(GameSettings.vapeInstance.getMappings().RM, this.I, f);
    }

    public boolean m$src$Z$1s8ei5l() {
        return MGameSettings.h$src$Z$1cffm5f(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    public KeyBinding y$src$Lgg_vape_wrapper_impl_KeyBinding_$1hvjjoh() {
        return new KeyBinding(MGameSettings.Y(GameSettings.vapeInstance.getMappings().RM, this.getObject()));
    }

    public boolean Y$src$Z$1rxemad() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return true;
        }
        return MGameSettings.F$src$Z$nptw01(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    public void P(boolean bl) {
        MGameSettings.m(GameSettings.vapeInstance.getMappings().RM, this.I, bl);
    }


    public List<String> f$src$Ljava_util_List_$1i0ug5l() {
        return MGameSettings.o(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    public KeyBinding Y() {
        return new KeyBinding(MGameSettings.l(GameSettings.vapeInstance.getMappings().RM, this.getObject()));
    }

    public int v() {
        if (ForgeVersion.MC_1_20_6.d()) {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.B(GameSettings.vapeInstance.getMappings().RM, this.I));
            return (Integer)gameSettingsValue.i();
        }
        return MGameSettings.t(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    public static void Y(String[] stringArray) {
        L = stringArray;
    }

    public KeyBinding F() {
        return new KeyBinding(MGameSettings.f(Vape.INSTANCE.getMappings().RM, this.getObject()));
    }

    public KeyBinding g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3() {
        return new KeyBinding(MGameSettings.F(GameSettings.vapeInstance.getMappings().RM, this.getObject()));
    }

    public int x() {
        if (ForgeVersion.MC_1_16_5.d()) {
            Object object = MGameSettings.g(GameSettings.vapeInstance.getMappings().RM, this.I);
            int n = 0;
            for (PointOfView pointOfView : PointOfView.values()) {
                if (pointOfView.getObject() == object) {
                    return n;
                }
                ++n;
            }
            return 0;
        }
        return MGameSettings.A(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    public float g() {
        return MGameSettings.s(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    public boolean M() {
        return MGameSettings.U$src$Z$16z50cw(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    public GameSettings(Object object) {
        super(object);
    }

    public KeyBinding O() {
        return new KeyBinding(MGameSettings.u(GameSettings.vapeInstance.getMappings().RM, this.getObject()));
    }

    public float y() {
        if (ForgeVersion.MC_1_20_6.d()) {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.k(GameSettings.vapeInstance.getMappings().RM, this.I));
            return (float)((Double)gameSettingsValue.i()).doubleValue();
        }
        return MGameSettings.V(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    public void k(float f) {
        MGameSettings.c(GameSettings.vapeInstance.getMappings().RM, this.I, f);
    }

    public int d() {
        return GameSettingsGuiScaleValue.getAntialiasingLevel();
    }

    public KeyBinding b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362() {
        return new KeyBinding(MGameSettings.U(Vape.INSTANCE.getMappings().RM, this.getObject()));
    }

    public KeyBinding j() {
        return new KeyBinding(MGameSettings.p(GameSettings.vapeInstance.getMappings().RM, this.getObject()));
    }

    public KeyBinding v$src$Lgg_vape_wrapper_impl_KeyBinding_$11ijh0e() {
        return new KeyBinding(MGameSettings.s$src$Ljava_lang_Object_$4nr0aa(GameSettings.vapeInstance.getMappings().RM, this.I));
    }

    public void R(int n) {
        if (ForgeVersion.MC_1_20_6.d()) {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.r(GameSettings.vapeInstance.getMappings().RM, this.getObject()));
            gameSettingsValue.a(n);
            return;
        }
        MGameSettings.D(GameSettings.vapeInstance.getMappings().RM, this.I, n);
    }

    public KeyBinding d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0() {
        return new KeyBinding(MGameSettings.v(GameSettings.vapeInstance.getMappings().RM, this.getObject()));
    }

    public boolean U() {
        return MGameSettings.D(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    public KeyBinding s() {
        return new KeyBinding(MGameSettings.y(GameSettings.vapeInstance.getMappings().RM, this.getObject()));
    }

    public int T() {
        if (ForgeVersion.MC_1_20_6.d()) {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.r(GameSettings.vapeInstance.getMappings().RM, this.getObject()));
            return (Integer)gameSettingsValue.i();
        }
        return MGameSettings.h(GameSettings.vapeInstance.getMappings().RM, this.getObject());
    }

    public void m(boolean bl) {
        MGameSettings.O(GameSettings.vapeInstance.getMappings().RM, this.I, bl);
    }

    public float f() {
        if (ForgeVersion.MC_1_20_6.d()) {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.w(GameSettings.vapeInstance.getMappings().RM, this.I));
            return (float)((Double)gameSettingsValue.i()).doubleValue();
        }
        return MGameSettings.H(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    public void F(boolean bl) {
        MGameSettings.Q(GameSettings.vapeInstance.getMappings().RM, this.I, bl);
    }

    public void I(int n) {
        if (ForgeVersion.MC_1_16_5.d()) {
            PointOfView pointOfView = PointOfView.values()[n];
            MGameSettings.E(GameSettings.vapeInstance.getMappings().RM, this.I, pointOfView.getObject());
            return;
        }
        MGameSettings.r(GameSettings.vapeInstance.getMappings().RM, this.I, n);
    }

    public KeyBinding r() {
        return new KeyBinding(MGameSettings.X(GameSettings.vapeInstance.getMappings().RM, this.I));
    }

    public static String[] E() {
        return L;
    }

    public float b() {
        if (ForgeVersion.MC_1_20_6.d()) {
            GameSettingsValue gameSettingsValue = new GameSettingsValue(MGameSettings.H$src$Ljava_lang_Object_$1jqua4d(GameSettings.vapeInstance.getMappings().RM, this.I));
            return (float)((Double)gameSettingsValue.i()).doubleValue();
        }
        return MGameSettings.C(GameSettings.vapeInstance.getMappings().RM, this.I);
    }

    static {
        if (GameSettings.E() == null) {
            GameSettings.Y(new String[1]);
        }
    }
}

