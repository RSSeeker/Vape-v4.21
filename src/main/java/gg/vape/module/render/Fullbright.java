package gg.vape.module.render;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPrePlayerTick;
import gg.vape.event.impl.EventPreRenderTick;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionRegistry;

public class Fullbright
extends Mod {
    private float currentGamma;
    private final BooleanValue fade;
    private boolean fadingOut;
    private boolean fadingIn;
    private final ModeOption gammaMode;
    private float fadeStep = 1.0f;
    private final TimerUtil fadeTimer;
    private float originalGamma = -1.0f;
    private final ModeValue mode;
    private final ModeOption nightVisionMode = new ModeOption("Night Vision");

    public void updateGammaFade() {
        if (!this.fadeTimer.hasTimeElapsed(10L)) {
            return;
        }
        this.fadeTimer.reset();
        this.fadeStep = 0.4f;
        if (this.fadingIn && this.currentGamma < 10.0f) {
            this.currentGamma += this.fadeStep;
            if (this.currentGamma >= 10.0f) {
                Minecraft.gameSettings().y(10.0f);
                this.fadingIn = false;
            } else {
                Minecraft.gameSettings().y(this.currentGamma);
            }
        }
        if (this.fadingOut && this.currentGamma >= this.originalGamma) {
            this.currentGamma -= this.fadeStep;
            if (this.currentGamma <= this.originalGamma) {
                Minecraft.gameSettings().y(this.originalGamma);
                this.fadingOut = false;
                super.setEnabled(false, true);
            } else {
                Minecraft.gameSettings().y(this.currentGamma);
            }
        }
    }

    @Override
    public void onEnable() {
        this.originalGamma = Minecraft.gameSettings().b();
        if (!this.fade.getEffectiveValue().booleanValue()) {
            Minecraft.gameSettings().y(10.0f);
        } else if (!this.fadingOut) {
            this.currentGamma = this.originalGamma;
            this.fadingIn = true;
        }
    }

    @EventHandler
    public void onTick(EventPrePlayerTick event) {
        if (((ModeSelection)this.mode.getValue()).equals(this.nightVisionMode)) {
            Minecraft.thePlayer().s(PotionEffect.o(PotionRegistry.T.getResolvedId(), 5220, 0));
            this.fadingIn = false;
        }
    }

    private void onModeChanged(ModeValue changedMode) {
        if (!this.isEnabled()) {
            return;
        }
        if (((ModeSelection)this.mode.getValue()).equals(this.gammaMode)) {
            Minecraft.thePlayer().q(16);
        } else if (this.originalGamma != -1.0f) {
            Minecraft.gameSettings().y(this.originalGamma);
        }
    }

    @Override
    public void setEnabled(boolean enabled, boolean bypassVisibilityCheck) {
        if (((ModeSelection)this.mode.getValue()).equals(this.gammaMode) && this.fade.getEffectiveValue().booleanValue() && this.isEnabled()) {
            this.fadingOut = true;
            this.fadingIn = false;
            return;
        }
        super.setEnabled(enabled, bypassVisibilityCheck);
    }

    @EventHandler
    public void onPreRenderTick(EventPreRenderTick event) {
        if (((ModeSelection)this.mode.getValue()).equals(this.gammaMode)) {
            this.updateGammaFade();
        }
    }

    @Override
    public void onDisable() {
        if (((ModeSelection)this.mode.getValue()).equals(this.nightVisionMode)) {
            if (Minecraft.thePlayer().isNotNull()) {
                Minecraft.thePlayer().q(PotionRegistry.T.getResolvedId());
                this.fadingOut = false;
                Minecraft.gameSettings().y(this.originalGamma);
            }
        } else {
            if (Minecraft.thePlayer().isNotNull()) {
            Minecraft.thePlayer().q(PotionRegistry.T.getResolvedId());
            }
            if (!this.fade.getEffectiveValue().booleanValue()) {
                Minecraft.gameSettings().y(this.originalGamma);
            }
        }
    }

    public Fullbright() {
        super("Fullbright", -256, Category.RENDER);
        this.gammaMode = new ModeOption("Gamma");
        this.mode = ModeValue.create((Object)this, "Mode", this.nightVisionMode, this.nightVisionMode, this.gammaMode);
        this.fade = BooleanValue.create(this, "Fade", false, "Brightness changes will fade in or out");
        this.fadeTimer = new TimerUtil();
        this.mode.addActiveMode(this.fade, this.gammaMode);
        this.addValue(this.mode, this.fade);
        this.mode.addChangeListener(this::onModeChanged);
    }

}

