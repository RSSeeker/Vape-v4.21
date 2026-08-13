package gg.vape.ui.click.component;

import func.skidline.RectData;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.GlStateManager;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

public class MarqueeTextRendererComponent
extends GuiComponent {
    private TimerUtil scrollTimer = new TimerUtil();
    GuiComponent hoverTrigger;
    private double scrollOffset = 0.0;

    @Override
    public void g(GuiMouseEvent mouseEvent) {
    }


    public void render(String text, double x, double y, double width, double fontScale, Color textColor) {
        this.renderInternal(text, x, y, width, fontScale, false, textColor, null);
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public void F() {
    }

    public void renderWithFade(String text, double x, double y, double width, double fontScale, Color textColor, @Nullable Color fadeColor) {
        this.renderInternal(text, x, y, width, fontScale, false, textColor, fadeColor);
    }

    @Override
    public void H() {
    }

    public MarqueeTextRendererComponent(@Nullable GuiComponent hoverTrigger) {
        this.hoverTrigger = hoverTrigger;
    }

    @Override
    public void I() {
    }

    public void renderInternal(String text, double x, double y, double width, double fontScale, boolean bold, Color textColor, @Nullable Color fadeColor) {
        SmoothFontRenderer fontRenderer = bold ? this.getAlternateFontRenderer(fontScale) : this.getFontRenderer(fontScale);
        double textWidth = fontRenderer.N(text);
        double textHeight = fontRenderer.d(text);
        RectData clipBounds = new RectData(x, y, width, textHeight);
        if (this.hoverTrigger != null && this.hoverTrigger.w$src$Z$e457mb() && textWidth > clipBounds.e()) {
            RenderUtils.m(clipBounds.o() - 1.0, clipBounds.W(), clipBounds.e() + 1.0, clipBounds.R());
            fontRenderer.d(text + " " + text, clipBounds.o() - this.scrollOffset, clipBounds.W(), textColor);
            RenderUtils.T();
            if (this.scrollTimer.hasTimeElapsed(30L)) {
                this.scrollOffset += 0.25;
                this.scrollTimer.reset();
            }
            if (this.scrollOffset >= textWidth + fontRenderer.N(" ")) {
                this.scrollOffset = 0.0;
            }
        } else {
            this.scrollOffset = 0.0;
            RenderUtils.m(clipBounds.o() - 1.0, clipBounds.W(), clipBounds.e() + 1.0, clipBounds.R());
            fontRenderer.d(text, clipBounds.o() - this.scrollOffset, clipBounds.W(), textColor);
            RenderUtils.T();
        }
        Color resolvedFadeColor = fadeColor == null ? new Color(0, 0, 0, 0) : fadeColor;
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.fillGradientQuad(clipBounds.o() + 1.0, clipBounds.W(), clipBounds.o() + 1.0, clipBounds.W() + clipBounds.R(), clipBounds.o() - 3.0, clipBounds.W() + clipBounds.R(), clipBounds.o() - 3.0, clipBounds.W(), new Color(31, 30, 31, 0), resolvedFadeColor);
            BufferedGuiRenderPrimitives.fillGradientQuad(clipBounds.o() + clipBounds.e() - 3.0, clipBounds.W(), clipBounds.o() + clipBounds.e() - 3.0, clipBounds.W() + clipBounds.R(), clipBounds.o() + clipBounds.e(), clipBounds.W() + clipBounds.R(), clipBounds.o() + clipBounds.e(), clipBounds.W(), new Color(31, 30, 31, 0), resolvedFadeColor);
        } else {
            boolean blendEnabled = GL11.glIsEnabled((int)3042);
            boolean texture2DEnabled = GL11.glIsEnabled((int)3553);
            boolean lightingEnabled = GL11.glIsEnabled((int)2896);
            boolean alphaTestEnabled = GL11.glIsEnabled((int)3008);
            boolean cullFaceEnabled = GL11.glIsEnabled((int)2884);
            if (!blendEnabled) {
                GlStateManager.enableBlend();
            }
            if (texture2DEnabled) {
                GlStateManager.disableTexture2D();
            }
            if (lightingEnabled) {
                GlStateManager.disableLighting();
            }
            if (!alphaTestEnabled) {
                GlStateManager.enableAlpha();
            }
            if (cullFaceEnabled) {
                GlStateManager.Y();
            }
            GL11.glShadeModel((int)7425);
            RenderUtils.w(new Color(31, 30, 31, 0));
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)(clipBounds.o() + 1.0), (double)clipBounds.W());
            GL11.glVertex2d((double)(clipBounds.o() + 1.0), (double)(clipBounds.W() + clipBounds.R()));
            RenderUtils.w(resolvedFadeColor);
            GL11.glVertex2d((double)(clipBounds.o() - 3.0), (double)(clipBounds.W() + clipBounds.R()));
            GL11.glVertex2d((double)(clipBounds.o() - 3.0), (double)clipBounds.W());
            GL11.glEnd();
            RenderUtils.w(new Color(31, 30, 31, 0));
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)(clipBounds.o() + clipBounds.e() - 3.0), (double)clipBounds.W());
            GL11.glVertex2d((double)(clipBounds.o() + clipBounds.e() - 3.0), (double)(clipBounds.W() + clipBounds.R()));
            RenderUtils.w(resolvedFadeColor);
            GL11.glVertex2d((double)(clipBounds.o() + clipBounds.e()), (double)(clipBounds.W() + clipBounds.R()));
            GL11.glVertex2d((double)(clipBounds.o() + clipBounds.e()), (double)clipBounds.W());
            GL11.glEnd();
            GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
            GL11.glShadeModel((int)7424);
            if (!alphaTestEnabled) {
                GlStateManager.disableAlpha();
            }
            if (lightingEnabled) {
                GlStateManager.enableLighting();
            }
            if (!blendEnabled) {
                GlStateManager.disableBlend();
            }
            if (cullFaceEnabled) {
                GlStateManager.L();
            }
            if (texture2DEnabled) {
                GlStateManager.enableTexture2D();
            }
        }
    }

    @Override
    public void u() {
    }
}

