package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MTextureManager;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMainWindow
extends Mapping {
    private MappingField O;
    private MappingField G;
    private MappingField K;
    private MappingField i;
    private MappingField Y;
    private MappingField a;

    public static int C(MMainWindow mMainWindow, Object object) {
        return mMainWindow.a(object);
    }

    private float U(Object object) {
        if (this.a == null) {
            return 0.0f;
        }
        return this.a.getFloat(object);
    }

    private float g(Object object) {
        return this.K.getFloat(object);
    }

    private int a(Object object) {
        if (this.Y == null) {
            return 0;
        }
        return this.Y.getInt(object);
    }

    private float F(Object object) {
        return this.O.getFloat(object);
    }


    public static float e(MMainWindow mMainWindow, Object object) {
        return mMainWindow.F(object);
    }

    public static float E(MMainWindow mMainWindow, Object object) {
        return mMainWindow.r(object);
    }

    private int o(Object object) {
        if (this.G == null) {
            return 0;
        }
        return this.G.getInt(object);
    }

    public static int T(MMainWindow mMainWindow, Object object) {
        return mMainWindow.o(object);
    }

    public static float W(MMainWindow mMainWindow, Object object) {
        return mMainWindow.U(object);
    }

    public MMainWindow() {
        this(MTextureManager.getInitialControlFlowState());
    }

    private MMainWindow(int n) {
        super(MappedClasses.Yc);
        int n2 = n;
        if (n2 != 0) {
            Class<Float> clazz = Float.TYPE;
            boolean bl = true;
            String string = "v";
            MMainWindow mMainWindow = this;
            this.O = mMainWindow.J(string, bl, clazz);
            if (GuiComponent.getLegacyComponentState() == null) {
                MTextureManager.setTextureManagerControlFlowState(++n2);
            }
            return;
        }
        if (ForgeVersion.MC_26_1.d()) {
            Class<Float> clazz = Float.TYPE;
            boolean bl = true;
            String string = "u0";
            MMainWindow mMainWindow = this;
            this.K = mMainWindow.J(string, bl, clazz);
            Class<Float> clazz2 = Float.TYPE;
            boolean bl2 = true;
            String string2 = "v0";
            MMainWindow mMainWindow2 = this;
            this.O = this.J(string2, bl2, clazz2);
            Class<Float> clazz3 = Float.TYPE;
            boolean bl3 = true;
            String string3 = "u1";
            MMainWindow mMainWindow3 = this;
            this.i = this.J(string3, bl3, clazz3);
            Class<Float> clazz4 = Float.TYPE;
            boolean bl4 = true;
            String string4 = "v1";
            MMainWindow mMainWindow4 = this;
            this.a = this.J(string4, bl4, clazz4);
        } else {
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "x";
            MMainWindow mMainWindow = this;
            this.G = mMainWindow.J(string, bl, clazz);
            Class<Integer> clazz5 = Integer.TYPE;
            boolean bl5 = true;
            String string5 = "y";
            MMainWindow mMainWindow5 = this;
            this.Y = this.J(string5, bl5, clazz5);
            Class<Float> clazz6 = Float.TYPE;
            boolean bl6 = true;
            String string6 = "u";
            MMainWindow mMainWindow6 = this;
            this.K = this.J(string6, bl6, clazz6);
            Class<Float> clazz7 = Float.TYPE;
            boolean bl7 = true;
            String string7 = "v";
            MMainWindow mMainWindow7 = this;
            this.O = this.J(string7, bl7, clazz7);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MTextureManager.setTextureManagerControlFlowState(++n2);
        }
    }

    public static float y(MMainWindow mMainWindow, Object object) {
        return mMainWindow.g(object);
    }

    private float r(Object object) {
        if (this.i == null) {
            return 0.0f;
        }
        return this.i.getFloat(object);
    }
}

