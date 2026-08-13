package gg.vape.ui.click.component;

import gg.vape.utils.TimerUtil;

public class ClickCooldownState {
    long cooldownMillis = 0L;
    boolean active = false;
    TimerUtil timer = new TimerUtil();

    public long getCooldownMillis() {
        return this.cooldownMillis;
    }


    public void setCooldownMillis(long cooldownMillis) {
        this.cooldownMillis = cooldownMillis;
    }

    public boolean isCoolingDown() {
        return this.active && !this.timer.hasTimeElapsed(this.cooldownMillis);
    }

    public void setActive(boolean active) {
        if (active) {
            this.active = true;
            this.timer.reset();
        } else {
            this.active = false;
        }
    }
}

