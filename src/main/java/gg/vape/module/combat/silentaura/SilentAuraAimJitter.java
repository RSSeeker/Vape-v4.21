package gg.vape.module.combat.silentaura;

import gg.vape.utils.MathUtil;
import gg.vape.utils.TimerUtil;
import java.util.Random;

public class SilentAuraAimJitter {
    private final Random random = new Random();
    private final double minValue;
    private double current;
    private final TimerUtil retargetTimer = new TimerUtil();
    private double target;
    private final double maxValue;

    public double getCurrentOffset() {
        return this.current;
    }

    public void update() {
        if (this.retargetTimer.hasTimeElapsed(MathUtil.randomExclusiveUpper(this.random, 100, 1000))) {
            this.retargetTimer.reset();
            this.target = MathUtil.randomRange(this.random, this.minValue, this.maxValue);
        }
        if (this.target > this.current) {
            this.current += 0.01 + MathUtil.randomRange(this.random, 0.0, 0.05);
            if (this.current > this.target) {
                this.current = this.target;
            }
        } else if (this.target < this.current) {
            this.current -= 0.01 + MathUtil.randomRange(this.random, 0.0, 0.05);
            if (this.current < this.target) {
                this.current = this.target;
            }
        }
    }


    public SilentAuraAimJitter(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}
