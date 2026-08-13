package gg.vape.module.blatant;

import gg.vape.Vape;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Minecraft;

public class KnockbackTest
extends Mod {
    private static final long MODULE_ID = -9039312205522206588L;
    private final NumberValue zValue;
    private final NumberValue xValue = NumberValue.create((Object)this, "x", "#.##", "", -2.0, 0.5, 2.0, 0.01);
    private final NumberValue yValue = NumberValue.create((Object)this, "y", "#.##", "", -2.0, 0.5, 2.0, 0.01);

    @Override
    public void onEnable() {
        Vape.debugLog("Trying to knockback " + Minecraft.thePlayer().z() + " " + Minecraft.thePlayer().N() + " " + Minecraft.thePlayer().h());
        Minecraft.thePlayer().E((Double)this.xValue.getValue(), (Double)this.yValue.getValue(), (Double)this.zValue.getValue());
        this.setEnabled(false);
    }

    public KnockbackTest() {
        super("Knockback Test", (int)MODULE_ID, Category.WORLD);
        this.zValue = NumberValue.create((Object)this, "z", "#.##", "", -2.0, 0.5, 2.0, 0.01);
        this.addValue(this.xValue, this.yValue, this.zValue);
    }
}

