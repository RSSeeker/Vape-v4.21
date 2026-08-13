package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.render.hud.PotionEffectsHudModule;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.font.FontFamily;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.PotionEffectIconRenderer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.I18n;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Potion;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.StatusEffect;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PotionEffectsHudFrame
extends HudModuleConfigFrameBase {
    private static final String FRAME_NAME = "PotionStatusFrame";
    private int maximumTextWidth;
    private final PotionEffectsHudModule module = (PotionEffectsHudModule)this.getModule();
    private static final Map<Integer, Integer> maximumDurationsByEffectId = new HashMap<Integer, Integer>();

    public PotionEffectsHudFrame() {
        super(PotionEffectsHudModule.class);
    }

    private void drawDurationRing(float x, float y, PotionEffect effect) {
        float remainingPercent = this.getRemainingPercent(effect.k(),
                maximumDurationsByEffectId.get(effect.C()).floatValue());
        Color color = PotionEffectsHudFrame.J.B;
        if (remainingPercent > 25.0f && remainingPercent <= 50.0f) {
            color = PotionEffectsHudFrame.J.I;
        } else if (remainingPercent <= 25.0f) {
            color = PotionEffectsHudFrame.J.d;
        }
        float arcDegrees = 360.0f * (remainingPercent / 100.0f);
        GuiRenderPrimitives.m(x - 0.5f, y - 0.5f, 21.25f, 1.8f, 1.0f,
                this.applyDefaultEditorAlpha(new Color(0, 0, 0, 200)));
        if (arcDegrees == 360.0f) {
            GuiRenderPrimitives.m(x - 1.0f, y - 1.0f, 22.0f, 2.5f, 1.0f,
                    this.applyDefaultEditorAlpha(color));
        } else {
            GuiRenderPrimitives.p(x - 1.0f, y - 1.0f, 22.0f, 2.0f, 0.5f,
                    270.0f, -arcDegrees, this.applyDefaultEditorAlpha(color));
        }
    }

    @Override
    public double A() {
        return 40 + this.maximumTextWidth;
    }

    private String formatDuration(int durationTicks) {
        int totalSeconds = durationTicks / 20;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds - minutes * 60;
        String minuteText = String.valueOf(minutes);
        String secondText = String.valueOf(seconds);
        if (minutes < 10) {
            minuteText = "0" + minuteText;
        }
        if (seconds < 10) {
            secondText = "0" + secondText;
        }
        return minuteText + ":" + secondText;
    }

    @Override
    public void renderHudContent() {
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        ArrayList<PotionEffect> effects = this.getActiveEffects();
        if (effects.isEmpty()) {
            if (!maximumDurationsByEffectId.isEmpty()) {
                maximumDurationsByEffectId.clear();
            }
            return;
        }
        double effectY = this.n();
        double rowHeight = 30.0;
        this.maximumTextWidth = 0;
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().K(0.85, true);
        if (I18n.getLanguage().isUnicode()) {
            smoothFontRenderer = Vape.INSTANCE.getFontManager().b(FontFamily.NOTO, 0.85f, false);
        }
        for (PotionEffect effect : effects) {
            boolean hidden = false;
            LinkedList<Integer> effectIdsToRemove = new LinkedList<Integer>();
            if (!this.isNegative(effect) && !this.module.showPositiveEffects.getEffectiveValue().booleanValue()) {
                effectIdsToRemove.add(this.getEffectId(effect));
                hidden = true;
            }
            if (this.isNegative(effect) && !this.module.showNegativeEffects.getEffectiveValue().booleanValue()) {
                effectIdsToRemove.add(this.getEffectId(effect));
                hidden = true;
            }
            if (!maximumDurationsByEffectId.containsKey(effect.C())
                    || maximumDurationsByEffectId.get(effect.C()) < effect.k()) {
                maximumDurationsByEffectId.put(effect.C(), effect.k());
            }
            ArrayList<Integer> activeEffectIds = new ArrayList<Integer>();
            for (PotionEffect activeEffect : effects) {
                activeEffectIds.add(new PotionEffect(activeEffect).C());
            }
            for (Integer trackedEffectId : maximumDurationsByEffectId.keySet()) {
                if (activeEffectIds.contains(trackedEffectId)) continue;
                effectIdsToRemove.add(trackedEffectId);
            }
            for (Integer effectId : effectIdsToRemove) {
                maximumDurationsByEffectId.remove(effectId);
            }
            if (hidden) continue;
            float remainingPercent = this.getRemainingPercent(effect.k(),
                    maximumDurationsByEffectId.get(effect.C()).floatValue());
            Color color = PotionEffectsHudFrame.J.A;
            if (remainingPercent > 50.0f && remainingPercent <= 100.0f) {
                color = PotionEffectsHudFrame.J.B;
            } else if (remainingPercent > 25.0f && remainingPercent <= 50.0f) {
                color = PotionEffectsHudFrame.J.I;
            } else if (remainingPercent <= 25.0f) {
                color = PotionEffectsHudFrame.J.d;
            }
            this.drawDurationRing((float)(this.G$src$D$1b2f02a() + 6.0),
                    (float)(effectY + 6.0), effect);
            Color dividerColor = ColorUtil.withAlpha(Color.WHITE, 51);
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 30.0, effectY + 10.0,
                    11.5, 2.0f, dividerColor);
            String effectName = ForgeVersion.MC_1_16_5.d()
                    ? effect.i().d()
                    : I18n.format(Potion.getPotionById(effect.C())
                            .y$src$Ljava_lang_String_$yl6pfj(), new Object[0]);
            String durationText = this.formatDuration(effect.k());
            int effectNameWidth = (int)smoothFontRenderer.N(effectName);
            if (effectNameWidth > this.maximumTextWidth) {
                this.maximumTextWidth = effectNameWidth;
            }
            int durationWidth = (int)smoothFontRenderer.N(durationText);
            if (durationWidth > this.maximumTextWidth) {
                this.maximumTextWidth = durationWidth;
            }
            smoothFontRenderer.d(effectName, this.G$src$D$1b2f02a() + 35.0,
                    effectY + 9.0, this.getEditorForegroundColor());
            smoothFontRenderer.T(durationText, this.G$src$D$1b2f02a() + 35.0,
                    effectY + 17.0, this.applyDefaultEditorAlpha(color), this.applyDefaultEditorAlpha(new Color(50, 50, 50, 150)));
            PotionEffectIconRenderer.render(effect, (int)(this.G$src$D$1b2f02a() + 9.0),
                    (int)(effectY + 10.0), 14, 14, this.getEditorOpacity());
            effectY += rowHeight;
        }
    }

    private int getEffectId(PotionEffect potionEffect) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return StatusEffect.v(potionEffect.i());
        }
        return Potion.getPotionById(potionEffect.C()).getId();
    }

    private ArrayList<PotionEffect> getActiveEffects() {
        EntityPlayerSP player = Minecraft.thePlayer();
        Collection collection = player.B$src$Ljava_util_Collection_$1uxz2f9();
        ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
        for (Object effectObject : collection) {
            effects.add(new PotionEffect(effectObject));
        }
        if (effects.isEmpty() && !ClientSettings.INSTANCE.inputEnabled) {
            effects.add(PotionEffect.o(1, 6500, 0));
            effects.add(PotionEffect.o(2, 5000, 0));
            effects.add(PotionEffect.o(12, 1000, 0));
            if (!(maximumDurationsByEffectId.containsKey(1)
                    && maximumDurationsByEffectId.containsKey(2)
                    && maximumDurationsByEffectId.containsKey(12))) {
                maximumDurationsByEffectId.put(1, 10000);
                maximumDurationsByEffectId.put(2, 10000);
                maximumDurationsByEffectId.put(12, 10000);
            }
        }
        return effects;
    }


    @Override
    public double L() {
        int effectCount = maximumDurationsByEffectId.size();
        if (effectCount == 0) {
            return ClientSettings.INSTANCE.inputEnabled ? 0.0 : 20.0;
        }
        return 2 + effectCount * 30;
    }

    @Override
    public String getName() {
        return FRAME_NAME;
    }

    private float getRemainingPercent(float remainingDuration, float maximumDuration) {
        return remainingDuration / maximumDuration * 100.0f;
    }

    private boolean isNegative(PotionEffect potionEffect) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return !potionEffect.i().p();
        }
        return Potion.getPotionById(potionEffect.C()).n();
    }
}
