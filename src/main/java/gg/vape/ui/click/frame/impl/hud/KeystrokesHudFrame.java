package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.input.KeyboardCodeUtil;
import gg.vape.input.KeyboardInput;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.hud.KeystrokesHudModule;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.click.frame.impl.hud.KeystrokesCpsCounterComponent;
import gg.vape.ui.click.frame.impl.hud.KeystrokesKeyComponent;
import gg.vape.ui.click.frame.impl.hud.KeystrokesMouseButtonComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import java.util.ArrayList;

public class KeystrokesHudFrame
extends HudModuleConfigFrameBase {
    private boolean syntheticAttackPressed;
    private final KeystrokesKeyComponent forwardKey;
    private final ArrayList<KeystrokesKeyComponent> keys;
    private boolean inputDisabled;
    private long syntheticAttackReleaseAt;
    public final KeystrokesCpsCounterComponent cpsCounter;
    private final GameSettings gameSettings = Minecraft.gameSettings();
    private final KeystrokesKeyComponent spacebar;
    private final KeystrokesHudModule keystrokesModule;
    private final KeystrokesKeyComponent leftMouseButton;
    private final KeystrokesKeyComponent rightMouseButton;
    private final KeystrokesKeyComponent rightKey;
    private final KeystrokesKeyComponent backwardKey;
    private final KeystrokesKeyComponent leftKey;

    @Override
    public String getName() {
        return "KeystrokesFrame";
    }

    private void drawMouseIcons(double x, double y) {
        ImageRenderer.drawResWithShadow(this.applyDefaultEditorAlpha(this.leftMouseButton.getBackgroundColorAnimation().getInterpolatedColor()), (float)x, (float)y, "lmb", 0.75f, false);
        ImageRenderer.drawResWithShadow(this.applyDefaultEditorAlpha(this.rightMouseButton.getBackgroundColorAnimation().getInterpolatedColor()), (float)(x + 20.0), (float)y, "rmb", 0.75f, false);
        ImageRenderer.drawResWithShadow(this.applyDefaultEditorAlpha(new Color(225, 225, 225)), (float)(x + 21.5), (float)(y + 7.0), "mmb", 0.216f, false);
    }

    private boolean shouldShowSpacebar() {
        if (this.keystrokesModule == null) {
            return false;
        }
        return this.keystrokesModule.showSpacebar.getEffectiveValue();
    }

    public void registerSyntheticAttack() {
        this.updateKeyCode(this.leftMouseButton.keyBinding.getKeyCode(), true, false);
        this.syntheticAttackPressed = true;
        this.syntheticAttackReleaseAt = System.currentTimeMillis() + 25L;
        this.cpsCounter.recordClick(0);
    }

    private void drawKey(KeystrokesKeyComponent key, double x, double y,
            double width, double height, String iconName) {
        KeyBinding keyBinding = key.keyBinding;
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().W(0.9, false);
        GuiRenderPrimitives.I(x, y - 0.5, width, height + 0.5,
                this.applyDefaultEditorAlpha(key.getBackgroundColorAnimation().getInterpolatedColor()),
                !key.isReleased(), 1.0f, 1.0f, 3.0f, KeystrokesHudFrame.J.u);
        if (key.equals(this.spacebar)) {
            double spacebarIndicatorWidth = 30.0;
            GuiRenderPrimitives.C(x + width / 2.0 - spacebarIndicatorWidth / 2.0,
                    y + 2.0, spacebarIndicatorWidth, 1.5,
                    this.applyEditorAlpha(key.getTextColorAnimation().getInterpolatedColor(), 10));
        }
        if (iconName != null) {
            float iconSize = 4.4f;
            ImageRenderer.drawImage(this.applyDefaultEditorAlpha(key.getTextColorAnimation().getInterpolatedColor()),
                    (float)(x + width / 2.5 - 4.0), (float)y + 2.0f,
                    iconName, iconSize, iconSize, false);
        } else {
            String label = key == this.leftMouseButton ? "LMB"
                    : (key == this.rightMouseButton ? "RMB"
                    : (key == this.spacebar ? "" : KeyboardInput.getKeyName(keyBinding.getKeyCode())));
            smoothFontRenderer.d(label, x + width / 2.5 - smoothFontRenderer.N(label) / 2.0,
                    y + 3.0, this.applyDefaultEditorAlpha(key.getTextColorAnimation().getInterpolatedColor()));
        }
    }

    public void syncPhysicalKeyStates() {
        for (KeystrokesKeyComponent key : this.keys) {
            this.updateKeyState(key.keyBinding, key.keyBinding.isKeyDown());
        }
    }

    private boolean usesArrowKeyStyle() {
        if (this.keystrokesModule == null) {
            return false;
        }
        return this.keystrokesModule.keyStyle.getValue() == this.keystrokesModule.arrowKeyStyle;
    }

    public void releaseAllKeys() {
        for (KeystrokesKeyComponent key : this.keys) {
            this.updateKeyState(key.keyBinding, false);
        }
    }

    public void updateKeyState(KeyBinding keyBinding, boolean pressed) {
        this.updateKeyCode(keyBinding.getKeyCode(), pressed, false);
    }

    public void handleKeyEvent(EventKeyPress event) {
        this.updateKeyCode(event.getKey(), event.isDown(), ForgeVersion.MC_1_16_5.v());
    }

    @Override
    public double L() {
        if (this.keystrokesModule != null && this.keystrokesModule.showCpsOnly.getEffectiveValue().booleanValue()) {
            return 20.0;
        }
        double height = 0.0;
        if (this.usesMouseIconStyle()) {
            height += 40.0;
            if (this.shouldShowSpacebar()) {
                height += 14.0;
            }
        } else {
            height += 72.0;
            if (this.shouldShowSpacebar()) {
                height += 14.0;
            }
        }
        return height;
    }

    @Override
    public boolean shouldRenderHudBackground() {
        return this.shouldRenderBackground() && this.keystrokesModule.showCpsOnly.getEffectiveValue() == false;
    }

    public boolean shouldRenderBackground() {
        return super.shouldRenderHudBackground();
    }

    private boolean usesMouseIconStyle() {
        if (this.keystrokesModule == null) {
            return false;
        }
        return this.keystrokesModule.mouseStyle.getValue() == this.keystrokesModule.mouseIconStyle;
    }

    @Override
    public void u() {
        this.releaseSyntheticAttackIfDue();
        boolean disabled = !ClientSettings.INSTANCE.isInputEnabled();
        if (this.inputDisabled == disabled) {
            return;
        }
        if (this.inputDisabled) {
            this.releaseAllKeys();
        } else {
            this.syncPhysicalKeyStates();
        }
        this.inputDisabled = disabled;
    }

    public KeystrokesCpsCounterComponent getCpsCounter() {
        return this.cpsCounter;
    }

    private void updateKeyCode(int keyCode, boolean pressed, boolean translateKeyCode) {
        for (KeystrokesKeyComponent key : this.keys) {
            int boundKeyCode = key.keyBinding.getKeyCode();
            if (boundKeyCode < 0) continue;
            if (translateKeyCode) {
                boundKeyCode = KeyboardCodeUtil.convertLegacyKeyCode(boundKeyCode);
            }
            if (boundKeyCode != keyCode) continue;
            if (pressed != key.pressed) {
                key.press();
                if (!pressed && !key.isReleased()) {
                    key.release();
                }
            }
            key.pressed = pressed;
        }
    }

    private void releaseSyntheticAttackIfDue() {
        if (!this.syntheticAttackPressed || System.currentTimeMillis() < this.syntheticAttackReleaseAt) {
            return;
        }
        this.syntheticAttackPressed = false;
        if (this.leftMouseButton.pressed && !this.leftMouseButton.keyBinding.isKeyDown()) {
            this.updateKeyCode(this.leftMouseButton.keyBinding.getKeyCode(), false, false);
        }
    }

    public KeystrokesHudFrame() {
        super(KeystrokesHudModule.class);
        this.keys = new ArrayList();
        this.forwardKey = new KeystrokesKeyComponent(this, this.gameSettings.Y());
        this.backwardKey = new KeystrokesKeyComponent(this, this.gameSettings.s());
        this.leftKey = new KeystrokesKeyComponent(this, this.gameSettings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg());
        this.rightKey = new KeystrokesKeyComponent(this, this.gameSettings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3());
        this.leftMouseButton = new KeystrokesMouseButtonComponent(this, this.gameSettings.F());
        this.rightMouseButton = new KeystrokesMouseButtonComponent(this, this.gameSettings.b$src$Lgg_vape_wrapper_impl_KeyBinding_$1yi3362());
        this.spacebar = new KeystrokesMouseButtonComponent(this, this.gameSettings.O());
        this.keystrokesModule = (KeystrokesHudModule)this.getModule();
        this.cpsCounter = new KeystrokesCpsCounterComponent(this);
        this.addChildren(this.cpsCounter);
        this.keys.add(this.forwardKey);
        this.keys.add(this.backwardKey);
        this.keys.add(this.leftKey);
        this.keys.add(this.rightKey);
        this.keys.add(this.leftMouseButton);
        this.keys.add(this.rightMouseButton);
        this.keys.add(this.spacebar);
    }

    @Override
    public double A() {
        if (this.keystrokesModule != null && this.keystrokesModule.showCpsOnly.getEffectiveValue().booleanValue()) {
            return 75.0;
        }
        double width = 54.0;
        if (this.usesMouseIconStyle()) {
            width += 48.0;
        }
        return width;
    }

    public void handleMouseEvent(EventMouseButton event) {
        for (KeystrokesKeyComponent key : this.keys) {
            int keyCode = key.keyBinding.getKeyCode();
            if (ForgeVersion.MC_1_16_5.v()) {
                keyCode += 100;
            }
            if (keyCode != event.getButton()) continue;
            if (event.getButtonState() != key.pressed) {
                key.press();
                if (!event.getButtonState() && !key.isReleased()) {
                    key.release();
                }
            }
            key.pressed = event.getButtonState();
        }
    }

    @Override
    public void renderHudContent() {
        double mouseY;
        double mouseX;
        double keysY;
        if (this.keystrokesModule != null && this.keystrokesModule.showCpsOnly.getEffectiveValue().booleanValue()) {
            this.setBackgroundSettingSuppressed(true);
            this.cpsCounter.setSingleButtonMode(true);
            this.cpsCounter.K(this.G$src$D$1b2f02a());
            this.cpsCounter.S(this.n());
            this.cpsCounter.o(55.0);
            this.cpsCounter.Y(10.0);
            return;
        }
        this.setBackgroundSettingSuppressed(false);
        this.cpsCounter.setSingleButtonMode(false);
        keysY = this.usesMouseIconStyle() ? this.n() + 4.0 : this.n() + 2.0;
        if (this.usesMouseIconStyle()) {
            mouseX = this.G$src$D$1b2f02a() + 61.0;
            mouseY = keysY - 6.0;
        } else {
            mouseX = this.G$src$D$1b2f02a();
            mouseY = keysY + 40.0;
        }
        this.drawKey(this.forwardKey, this.G$src$D$1b2f02a() + 19.0, keysY - 2.0,
                17.0, 17.0, this.usesArrowKeyStyle() ? "up" : null);
        this.drawKey(this.leftKey, this.G$src$D$1b2f02a(), keysY + 19.0,
                17.0, 17.0, this.usesArrowKeyStyle() ? "left" : null);
        this.drawKey(this.backwardKey, this.G$src$D$1b2f02a() + 19.0, keysY + 19.0,
                17.0, 17.0, this.usesArrowKeyStyle() ? "down" : null);
        this.drawKey(this.rightKey, this.G$src$D$1b2f02a() + 38.0, keysY + 19.0,
                17.0, 17.0, this.usesArrowKeyStyle() ? "right" : null);
        if (this.shouldShowSpacebar()) {
            this.drawKey(this.spacebar, this.G$src$D$1b2f02a(), keysY + 39.5,
                    55.25, 11.0, null);
            mouseY += 14.0;
        }
        if (this.usesMouseIconStyle()) {
            this.drawMouseIcons(mouseX - 2.0, mouseY);
            this.cpsCounter.o(40.0);
        } else {
            this.drawKey(this.leftMouseButton, mouseX, mouseY, 26.35, 16.0, null);
            this.drawKey(this.rightMouseButton, mouseX + 28.35, mouseY, 26.35, 16.0, null);
            this.cpsCounter.o(55.0);
        }
        double cpsOffsetY = this.usesMouseIconStyle() ? 22.0 : 17.0;
        this.cpsCounter.K(mouseX);
        this.cpsCounter.S(mouseY + cpsOffsetY);
        this.cpsCounter.Y(10.0);
        this.leftMouseButton.backgroundColorAnimation.U(0.01);
    }

}

