package gg.vape.module.blatant;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Minecraft;

public class Timer
extends Mod {
    private static final long MODULE_ID = 853958073918623760L;
    private final NumberValue speedValue = NumberValue.create((Object)this, "Speed", "#.##", "", 0.1, 1.07, 2.0, 0.01);
    private boolean tickParity;

    @EventHandler
    public void onTick(EventPrePlayerTick eventPrePlayerTick) {
        this.tickParity = !this.tickParity;
        Minecraft.getTimer().setTimerSpeed(((Double)this.speedValue.getValue()).floatValue());
    }

    public Timer() {
        super("Timer", (int)MODULE_ID, Category.OTHER, "Modifies game timer");
        this.setDefaultVisibility(false);
        this.addValue(this.speedValue);
    }

    @Override
    public void onDisable() {
        Minecraft.getTimer().setTimerSpeed(1.0f);
    }

}

