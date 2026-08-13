package gg.vape.module.blatant;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;

public class OmniSprint
extends Mod {
    private final RandomValue rate = RandomValue.create(this, "Rate", "#.#", "", 0.0, 1.0, 3.0, 10.0);
    private final NumberValue rateChange = NumberValue.create(this, "Rate Change", "#.#", "", 0.0, 1.0, 5.0);
    private float currentRate;
    private long lastUpdateTime;

    @EventHandler
    public void onTick(EventPrePlayerTick eventPrePlayerTick) {
    }

    public OmniSprint() {
        super("OmniSprint", -256, Category.OTHER);
        this.addValue(this.rate, this.rateChange);
    }
}

