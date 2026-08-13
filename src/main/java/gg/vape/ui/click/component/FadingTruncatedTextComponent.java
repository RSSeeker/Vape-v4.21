package gg.vape.ui.click.component;

import func.skidline.RectData;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.text.TextTruncationIndexCache;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.GlStateManager;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class FadingTruncatedTextComponent
extends TruncatedTextComponent {
    private Color fadeColor;

    @Override
    public double getRenderedWidth() {
        int truncationIndex = TextTruncationIndexCache.INSTANCE.getTruncationIndex(this.textSpec);
        SmoothFontRenderer fontRenderer = this.textSpec.isBold() ? this.getAlternateFontRenderer(this.textSpec.getFontScale()) : this.getFontRenderer(this.textSpec.getFontScale());
        if (truncationIndex >= 0) {
            return fontRenderer.N(this.getText().substring(0, truncationIndex));
        }
        return 0.0;
    }

    public FadingTruncatedTextComponent(String text, double maxWidth, double fontScale, Color textColor, Color fadeColor, boolean bold, boolean drawShadow) {
        super(text, "", maxWidth, fontScale, textColor, bold, drawShadow);
        this.fadeColor = fadeColor;
    }

    public Color getFadeColor() {
        return this.fadeColor;
    }

    @Override
    public void renderAt(double x, double y) {
        int truncationIndex = TextTruncationIndexCache.INSTANCE.getTruncationIndex(this.textSpec);
        if (truncationIndex == this.getText().length() - 1) {
            SmoothFontRenderer fontRenderer = this.textSpec.isBold() ? this.getAlternateFontRenderer(this.textSpec.getFontScale()) : this.getFontRenderer(this.textSpec.getFontScale());
            if (this.drawShadow) {
                fontRenderer.v(this.getText(), x, y, this.textColor);
            } else {
                fontRenderer.d(this.getText(), x, y, this.textColor);
            }
        } else if (truncationIndex >= 0) {
            SmoothFontRenderer fontRenderer = this.textSpec.isBold() ? this.getAlternateFontRenderer(this.textSpec.getFontScale()) : this.getFontRenderer(this.textSpec.getFontScale());
            String visibleText = this.getText().substring(0, truncationIndex);
            double visibleTextWidth = fontRenderer.N(visibleText);
            double visibleTextHeight = fontRenderer.d(visibleText);
            RectData rectData = new RectData(x, y, this.getMaxWidth(), visibleTextHeight);
            RenderUtils.m(rectData.o() - 1.0, rectData.W(), rectData.e() + 1.0, rectData.R());
            if (this.drawShadow) {
                fontRenderer.v(visibleText, rectData.o(), rectData.W(), this.textColor);
            } else {
                fontRenderer.d(visibleText, rectData.o(), rectData.W(), this.textColor);
            }
            RenderUtils.T();
            if (GuiRenderPrimitives.d()) {
                MutableColor mutableColor = new MutableColor(this.fadeColor);
                mutableColor.withAlpha(0);
                BufferedGuiRenderPrimitives.fillGradientQuad(rectData.o() + rectData.e() - 6.0, rectData.W(), rectData.o() + rectData.e() - 6.0, rectData.W() + rectData.R(), rectData.o() + rectData.e(), rectData.W() + rectData.R(), rectData.o() + rectData.e(), rectData.W(), mutableColor, this.fadeColor == null ? new Color(0, 0, 0, 0) : this.fadeColor);
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
                MutableColor mutableColor = new MutableColor(this.fadeColor);
                mutableColor.withAlpha(0);
                RenderUtils.w(mutableColor);
                GL11.glBegin((int)7);
                GL11.glVertex2d((double)(rectData.o() + rectData.e() - 6.0), (double)rectData.W());
                GL11.glVertex2d((double)(rectData.o() + rectData.e() - 6.0), (double)(rectData.W() + rectData.R()));
                RenderUtils.w(this.fadeColor == null ? new Color(0, 0, 0, 0) : this.fadeColor);
                GL11.glVertex2d((double)(rectData.o() + rectData.e()), (double)(rectData.W() + rectData.R()));
                GL11.glVertex2d((double)(rectData.o() + rectData.e()), (double)rectData.W());
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
        boolean textWasTruncated = truncationIndex < this.textSpec.getText().length() - 1;
        boolean hasAdditionalTooltip = !this.additionalTooltipText.equals("");
        String tooltipText = (textWasTruncated ? this.textSpec.getText() : "") + (hasAdditionalTooltip ? (textWasTruncated ? "\n" + this.additionalTooltipText : this.additionalTooltipText) : "");
        this.w(tooltipText);
    }

    public void setFadeColor(Color fadeColor) {
        this.fadeColor = fadeColor;
    }

}

