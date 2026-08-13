package gg.vape.wrapper.impl;

import gg.vape.module.MinecraftVersionComparisonOperator;
import gg.vape.module.MinecraftVersionConstraint;
import gg.vape.runtime.NativeBridge;
import gg.vape.ui.click.component.GuiComponent;

public enum ForgeVersion {
    MC_1_7_10(13),
    MC_1_8_9(15),
    MC_1_12_2(23),
    MC_1_14_4(28),
    MC_1_16_5(35),
    MC_1_16_5_ACTUAL(36),
    MC_1_17(37),
    MC_1_20_6(50),
    MC_1_21_0(51),
    MC_1_21_4(54),
    MC_1_21_5(55),
    MC_1_21_6(56),
    MC_1_21_10(60),
    MC_1_21_11(61),
    MC_26_1(100),
    MC_26_2(110);

    public static int gameVersion;
    private final int H;
    private static GuiComponent[] v;

    private ForgeVersion(int protocolVersion) {
        this.H = protocolVersion;
    }

    public int i() {
        return this.H;
    }

    public int getProtocolVersion() {
        return this.H;
    }

    public static int c() {
        if (gameVersion == 0) {
            gameVersion = NativeBridge.gmv();
        }
        return gameVersion;
    }

    public static int getCurrentProtocolVersion() {
        return ForgeVersion.c();
    }

    public static void W(GuiComponent[] upArray) {
        v = upArray;
    }

    public static GuiComponent[] o() {
        return v;
    }

    public boolean B() {
        return this.H().y();
    }

    public MinecraftVersionConstraint h() {
        return new MinecraftVersionConstraint(this, MinecraftVersionComparisonOperator.NOT_EQUAL);
    }

    public boolean v() {
        return this.b().y();
    }

    public MinecraftVersionConstraint N() {
        return new MinecraftVersionConstraint(this, MinecraftVersionComparisonOperator.GREATER_THAN);
    }

    public boolean Y() {
        return this.N().y();
    }

    public MinecraftVersionConstraint H() {
        return new MinecraftVersionConstraint(this, MinecraftVersionComparisonOperator.LESS_THAN_OR_EQUAL);
    }

    public boolean L() {
        return this.S().y();
    }

    public MinecraftVersionConstraint n() {
        return new MinecraftVersionConstraint(this, MinecraftVersionComparisonOperator.GREATHER_THAN_OR_EQUAL);
    }

    public boolean A() {
        return this.h().y();
    }

    public MinecraftVersionConstraint b() {
        return new MinecraftVersionConstraint(this, MinecraftVersionComparisonOperator.LESS_THAN);
    }

    public MinecraftVersionConstraint S() {
        return new MinecraftVersionConstraint(this, MinecraftVersionComparisonOperator.EQUALS);
    }

    public boolean d() {
        return this.n().y();
    }

    static {
        v = new GuiComponent[5];
    }
}

