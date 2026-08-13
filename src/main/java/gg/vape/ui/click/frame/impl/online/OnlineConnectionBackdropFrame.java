package gg.vape.ui.click.frame.impl.online;

import gg.vape.ui.click.frame.Frame;
import gg.vape.utils.render.BlurRegionRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;

public class OnlineConnectionBackdropFrame
extends Frame {
    private final BlurRegionRenderer backgroundBlurRenderer = new BlurRegionRenderer(0, 0);

    @Override
    public double A() {
        return Minecraft.J() / 2;
    }

    @Override
    public void c() {
        int screenWidth = Minecraft.J();
        int screenHeight = Minecraft.h();
        GuiRenderPrimitives.y(0.0f, 0.0f, screenWidth, screenHeight, new Color(0, 0, 0, 100));
        this.backgroundBlurRenderer.setDimensions(screenWidth, screenHeight);
        int blurRadius = 20;
        this.backgroundBlurRenderer.renderBlur(0, 0, blurRadius, 0.0f);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public double L() {
        return Minecraft.h() / 2;
    }
}
