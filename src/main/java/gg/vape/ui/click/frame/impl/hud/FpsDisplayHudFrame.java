package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.module.render.hud.FpsDisplayHudModule;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

public class FpsDisplayHudFrame
extends HudModuleConfigFrameBase {
    private static final int SHADOW_COLOR_ARGB = 0x80000000;


    @Override
    public double A() {
        return 50.0;
    }

    @Override
    public void renderHudContent() {
        SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().W(1.2, false);
        String fpsText = Minecraft.l() + " FPS";
        float textX = (int)(this.G$src$D$1b2f02a() + this.A() / 2.0
                - smoothFontRenderer.N(fpsText) / 2.0);
        float textY = (int)(this.n() + this.L() / 2.0
                - smoothFontRenderer.d(fpsText) / 2.0);
        if (this.shouldRenderHudBackground()) {
            smoothFontRenderer.d(fpsText, textX, textY, this.getEditorForegroundColor());
        } else {
            smoothFontRenderer.T(fpsText, textX, textY,
                    this.getEditorForegroundColor(),
                    this.applyDefaultEditorAlpha(new Color(SHADOW_COLOR_ARGB, true)));
        }
    }

    public FpsDisplayHudFrame() {
        super(FpsDisplayHudModule.class);
    }

    @Override
    public String getName() {
        return "FpsFrame";
    }

    @Override
    public double L() {
        return 20.0;
    }
}

