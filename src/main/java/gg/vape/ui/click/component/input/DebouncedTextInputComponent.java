package gg.vape.ui.click.component.input;

import gg.vape.Vape;
import gg.vape.ui.click.component.ClickCooldownState;
import gg.vape.ui.click.component.TextInputComponentBase;

public class DebouncedTextInputComponent
extends TextInputComponentBase {
    private static final String UNHANDLED_COOLDOWN_MESSAGE = "Unhandled cooldown fail ";
    private final ClickCooldownState cooldown = new ClickCooldownState();

    public ClickCooldownState getCooldown() {
        return this.cooldown;
    }

    public void handleSubmitReady() {
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public void submit() {
        if (this.cooldown.isCoolingDown()) {
            this.handleSubmitCooldown();
            return;
        }
        this.handleSubmitReady();
        this.cooldown.setActive(true);
    }

    public DebouncedTextInputComponent(String text, long cooldownMillis) {
        super(text);
        this.cooldown.setCooldownMillis(cooldownMillis);
    }

    public void handleSubmitCooldown() {
        Vape.debugLog(UNHANDLED_COOLDOWN_MESSAGE + this);
    }

    @Override
    public double x() {
        return 0.0;
    }

}
