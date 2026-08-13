package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManagerTexGenState;

public class GlTextureUnitState {
    private static boolean samplerSupportEnabled;
    private static final int legacyMagicValue;
    private static final String SAMPLER_SUPPORT_MESSAGE;
    private static boolean initialized;
    private static final int[] savedTextureBindings;

    static {
        SAMPLER_SUPPORT_MESSAGE = "SamplerFix: GL33 sampler support enabled via hook";
        long legacyMagicLong = -700399992203902968L;
        legacyMagicValue = (int)legacyMagicLong;
        initialized = false;
        samplerSupportEnabled = false;
        savedTextureBindings = new int[8];
    }

    public static void restoreTextureUnitZero() {
        GlTextureUnitState.restoreTextureUnit(0);
    }

    @Deprecated
    public static void clearTextureUnits(int textureUnitCount) {
        if (!GlTextureUnitState.isSamplerSupportEnabled()) {
            return;
        }
        for (int textureUnit = 0; textureUnit < textureUnitCount; ++textureUnit) {
            GlTextureUnitState.clearTextureUnit(textureUnit);
        }
    }

    public static void saveAndClearTextureUnits(int textureUnitCount) {
        if (!GlTextureUnitState.isSamplerSupportEnabled()) {
            return;
        }
        int boundedTextureUnitCount = Math.min(textureUnitCount, 8);
        for (int textureUnit = 0; textureUnit < boundedTextureUnitCount; ++textureUnit) {
            GlTextureUnitState.saveAndClearTextureUnit(textureUnit);
        }
    }

    public static void restoreTextureUnit(int textureUnit) {
        if (!GlTextureUnitState.isSamplerSupportEnabled() || textureUnit >= 8) {
            return;
        }
        GlStateManagerTexGenState.I(textureUnit, savedTextureBindings[textureUnit]);
    }

    @Deprecated
    public static void clearTextureUnitZero() {
        GlTextureUnitState.clearTextureUnit(0);
    }

    public static void saveTextureUnit(int textureUnit) {
        if (!GlTextureUnitState.isSamplerSupportEnabled() || textureUnit >= 8) {
            return;
        }
        GlTextureUnitState.savedTextureBindings[textureUnit] = GlStateManagerTexGenState.J(textureUnit);
    }

    public static void saveAndClearTextureUnitZero() {
        GlTextureUnitState.saveAndClearTextureUnit(0);
    }

    public static void restoreTextureUnits(int textureUnitCount) {
        if (!GlTextureUnitState.isSamplerSupportEnabled()) {
            return;
        }
        int boundedTextureUnitCount = Math.min(textureUnitCount, 8);
        for (int textureUnit = 0; textureUnit < boundedTextureUnitCount; ++textureUnit) {
            GlTextureUnitState.restoreTextureUnit(textureUnit);
        }
    }

    public static void saveAndClearTextureUnit(int textureUnit) {
        if (!GlTextureUnitState.isSamplerSupportEnabled()) {
            return;
        }
        GlTextureUnitState.saveTextureUnit(textureUnit);
        GlTextureUnitState.clearTextureUnit(textureUnit);
    }

    public static void clearTextureUnit(int textureUnit) {
        if (!GlTextureUnitState.isSamplerSupportEnabled()) {
            return;
        }
        GlStateManagerTexGenState.I(textureUnit, 0);
    }

    public static boolean isSamplerSupportEnabled() {
        if (!initialized) {
            GlTextureUnitState.initializeSamplerSupport();
        }
        return samplerSupportEnabled;
    }

    public static void initializeSamplerSupport() {
        if (initialized) {
            return;
        }
        initialized = true;
        if (!GuiRenderPrimitives.d()) {
            samplerSupportEnabled = false;
            return;
        }
        if (ForgeVersion.MC_1_21_11.v()) {
            samplerSupportEnabled = false;
            return;
        }
        samplerSupportEnabled = GlStateManagerTexGenState.p();
        if (samplerSupportEnabled) {
            Vape.debugLog(SAMPLER_SUPPORT_MESSAGE);
        }
    }

}

