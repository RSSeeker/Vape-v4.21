package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.FadingTruncatedTextComponent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.TruncatedTextComponent;
import gg.vape.ui.click.text.TextLabelFitScaleCache;
import gg.vape.ui.click.text.TextLabelFitSpec;
import gg.vape.ui.click.text.TruncatedTextSpec;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;

public class TextLabelComponent
extends GuiComponent {
    private boolean drawShadow;
    private Color textColor;
    private FadingTruncatedTextComponent fadingText;
    private GuiComponent associatedComponent;
    private TruncatedTextComponent truncatedText;
    private boolean fadeWhenTruncated;
    private TextLabelFitSpec fitSpec;

    public boolean isShadowEnabled() {
        return this.drawShadow;
    }

    public boolean isFadeWhenTruncated() {
        return this.fadeWhenTruncated;
    }

    public void setFitSpec(TextLabelFitSpec fitSpec) {
        this.fitSpec = fitSpec;
    }

    public void setShadowEnabled(boolean drawShadow) {
        this.drawShadow = drawShadow;
    }

    public boolean isBold() {
        return this.fitSpec.isBold();
    }

    @Override
    public double x() {
        return this.getRenderedWidth();
    }

    public TextLabelComponent(String text, double minScale, double maxScale, double scaleIncrement, double maxWidth, boolean bold, boolean drawShadow, Color textColor) {
        this(text, minScale, maxScale, scaleIncrement, maxWidth, bold, drawShadow, textColor, null);
    }

    public void setMaxWidth(double maxWidth) {
        this.fitSpec.setMaxWidth(maxWidth);
    }

    @Override
    public void H() {
        this.renderAt(this.G$src$D$1b2f02a(), this.n() + this.C() / 2.0);
    }

    @Override
    public void u() {
    }

    public void setMaxScale(double maxScale) {
        this.fitSpec.setMaxScale(maxScale);
    }

    public double getTextHeight() {
        if (this.truncatedText != null) {
            return this.truncatedText.getTextHeight();
        }
        if (this.fadingText != null) {
            return this.fadingText.getTextHeight();
        }
        double fittedScale = TextLabelFitScaleCache.INSTANCE.getFittedScale(this.fitSpec);
        return this.fitSpec.isBold() ? this.getAlternateFontRenderer(fittedScale).d(this.fitSpec.getText()) : this.getFontRenderer(fittedScale).d(this.fitSpec.getText());
    }

    public GuiComponent getAssociatedComponent() {
        return this.associatedComponent;
    }

    @Override
    public double C() {
        return this.getTextHeight();
    }

    public double getRenderedWidth() {
        if (this.truncatedText != null) {
            return this.truncatedText.getRenderedWidth();
        }
        if (this.fadingText != null) {
            return this.fadingText.getRenderedWidth();
        }
        double fittedScale = TextLabelFitScaleCache.INSTANCE.getFittedScale(this.fitSpec);
        return this.fitSpec.isBold() ? this.getAlternateFontRenderer(fittedScale).N(this.fitSpec.getText()) : this.getFontRenderer(fittedScale).N(this.fitSpec.getText());
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    public TextLabelComponent(String text, double minScale, double maxScale, double scaleIncrement, double maxWidth, boolean bold, boolean drawShadow, Color textColor, GuiComponent associatedComponent) {
        this(text, minScale, maxScale, scaleIncrement, maxWidth, bold, drawShadow, textColor, associatedComponent, false);
    }

    public void setFadeWhenTruncated(boolean fadeWhenTruncated) {
        this.fadeWhenTruncated = fadeWhenTruncated;
    }

    @Override
    public void I() {
    }

    public TextLabelComponent(String text, double minScale, double maxScale, double scaleIncrement, double maxWidth, boolean bold, boolean drawShadow, Color textColor, GuiComponent associatedComponent, boolean fadeWhenTruncated) {
        this.fitSpec = new TextLabelFitSpec(text, minScale, maxScale, scaleIncrement, maxWidth, bold);
        this.textColor = textColor;
        this.drawShadow = drawShadow;
        this.associatedComponent = associatedComponent;
        this.fadeWhenTruncated = fadeWhenTruncated;
    }

    public void renderAt(double x, double centerY) {
        double fittedScale = TextLabelFitScaleCache.INSTANCE.getFittedScale(this.fitSpec);
        if (fittedScale >= this.fitSpec.getMinScale()) {
            SmoothFontRenderer fontRenderer = this.fitSpec.isBold() ? this.getAlternateFontRenderer(fittedScale) : this.getFontRenderer(fittedScale);
            double textY = centerY - fontRenderer.d(this.fitSpec.getText()) / 2.0;
            if (this.drawShadow) {
                fontRenderer.v(this.fitSpec.getText(), x, textY, this.textColor);
            } else {
                fontRenderer.d(this.fitSpec.getText(), x, textY, this.textColor);
            }
            this.setToolTips(null);
            this.truncatedText = null;
        } else if (this.fadeWhenTruncated) {
            if (this.fadingText == null) {
                this.fadingText = new FadingTruncatedTextComponent(this.fitSpec.getText(), this.fitSpec.getMaxWidth(), this.fitSpec.getMinScale(), this.textColor, null, this.isBold(), this.drawShadow);
            }
            this.synchronizeFadingText();
            this.fadingText.renderAt(x, centerY - this.getTextHeight() / 2.0);
            this.setToolTips(this.fadingText.getToolTips());
        } else {
            if (this.truncatedText == null) {
                this.truncatedText = new TruncatedTextComponent(this.fitSpec.getText(), "...", this.fitSpec.getMaxWidth(), this.fitSpec.getMinScale(), this.textColor, this.isBold(), this.drawShadow);
            }
            this.synchronizeTruncatedText();
            this.truncatedText.renderAt(x, centerY - this.getTextHeight() / 2.0);
            this.setToolTips(this.truncatedText.getToolTips());
        }
    }

    public double getMinScale() {
        return this.fitSpec.getMinScale();
    }

    public Color getTextColor() {
        return this.textColor;
    }

    public void setText(String text) {
        this.fitSpec.setText(text);
    }

    public TextLabelFitSpec getFitSpec() {
        return this.fitSpec;
    }

    public String getText() {
        return this.fitSpec.getText();
    }

    private void synchronizeFadingText() {
        this.fadingText.setTextSpec(new TruncatedTextSpec(this.fitSpec.getText(), "", this.fitSpec.getMaxWidth(), this.fitSpec.getMinScale(), this.fitSpec.isBold()));
        this.fadingText.setTextColor(this.getTextColor());
        this.fadingText.setShadowEnabled(this.isShadowEnabled());
    }

    public void setBold(boolean bold) {
        this.fitSpec.setBold(bold);
    }

    public void setFadeColor(Color fadeColor) {
        if (this.fadingText != null) {
            this.fadingText.setFadeColor(fadeColor);
        }
    }

    @Override
    public void S(double d) {
        super.S(d);
    }

    public double getScaleIncrement() {
        return this.fitSpec.getScaleIncrement();
    }

    public void setScaleIncrement(double scaleIncrement) {
        this.fitSpec.setScaleIncrement(scaleIncrement);
    }

    public void setMinScale(double minScale) {
        this.fitSpec.setMinScale(minScale);
    }

    public double getMaxScale() {
        return this.fitSpec.getMaxScale();
    }


    public void setAssociatedComponent(GuiComponent associatedComponent) {
        this.associatedComponent = associatedComponent;
    }

    @Override
    public void F() {
    }

    private void synchronizeTruncatedText() {
        this.truncatedText.setTextSpec(new TruncatedTextSpec(this.fitSpec.getText(), "...", this.fitSpec.getMaxWidth(), this.fitSpec.getMinScale(), this.fitSpec.isBold()));
        this.truncatedText.setTextColor(this.getTextColor());
        this.truncatedText.setShadowEnabled(this.isShadowEnabled());
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public double getMaxWidth() {
        return this.fitSpec.getMaxWidth();
    }
}

