package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.text.SuffixTextTruncationIndexCache;
import gg.vape.ui.click.text.TruncatedTextSpec;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.StringUtils;
import java.awt.Color;

public class TruncatedTextComponent
extends GuiComponent {
    public boolean drawShadow;
    public Color textColor;
    private boolean truncationTooltipEnabled = true;
    protected String additionalTooltipText = "";
    private static final String DEFAULT_TRUNCATION_SUFFIX = "...";
    protected String unusedLegacyText;
    public TruncatedTextSpec textSpec;
    private boolean centered = false;

    @Override
    public void I() {
    }

    @Override
    public void H() {
        this.renderAt(this.G$src$D$1b2f02a(), this.n());
    }

    public void setBold(boolean bold) {
        this.textSpec.setBold(bold);
    }

    @Override
    public void u() {
    }

    public void setFontScale(double fontScale) {
        this.textSpec.setFontScale(fontScale);
    }

    public double getTextHeight() {
        double fontScale = this.getFontScale();
        return this.isBold() ? this.getAlternateFontRenderer(fontScale).d(this.getText()) : this.getFontRenderer(fontScale).d(this.getText());
    }

    public TruncatedTextComponent(String text, String truncationSuffix, double maxWidth, double fontScale, Color textColor, boolean bold, boolean drawShadow) {
        this(text, truncationSuffix, "", maxWidth, fontScale, textColor, bold, drawShadow);
    }

    public double getFontScale() {
        return this.textSpec.getFontScale();
    }

    public void setAdditionalTooltipText(String additionalTooltipText) {
        this.additionalTooltipText = additionalTooltipText;
    }

    public TruncatedTextComponent(String text, String truncationSuffix, double maxWidth, double fontScale, Color textColor, boolean bold) {
        this(text, truncationSuffix, maxWidth, fontScale, textColor, bold, false);
    }

    public TruncatedTextComponent(String text, String truncationSuffix, String additionalTooltipText, double maxWidth, double fontScale, Color textColor, boolean bold, boolean drawShadow) {
        this.textSpec = new TruncatedTextSpec(text, truncationSuffix, maxWidth, fontScale, bold);
        this.textColor = textColor;
        this.drawShadow = drawShadow;
        this.additionalTooltipText = additionalTooltipText;
    }

    public TruncatedTextSpec getTextSpec() {
        return this.textSpec;
    }

    public String getText() {
        return this.textSpec.getText();
    }

    public TruncatedTextComponent(String text, double maxWidth, double fontScale) {
        this(text, DEFAULT_TRUNCATION_SUFFIX, maxWidth, fontScale, TruncatedTextComponent.J.A, false, false);
    }

    @Override
    public void F() {
    }

    public String getTruncationSuffix() {
        return this.textSpec.getTruncationSuffix();
    }

    public void renderAt(double x, double y) {
        if (this.getText().isEmpty()) {
            return;
        }
        int truncationIndex = SuffixTextTruncationIndexCache.INSTANCE.getTruncationIndex(this.textSpec);
        SmoothFontRenderer fontRenderer = this.textSpec.isBold() ? this.getAlternateFontRenderer(this.textSpec.getFontScale()) : this.getFontRenderer(this.textSpec.getFontScale());
        String displayedText = truncationIndex >= 0 ? (truncationIndex < this.textSpec.getText().length() - 1 ? this.textSpec.getText().substring(0, truncationIndex) + this.textSpec.getTruncationSuffix() : this.textSpec.getText()) : this.textSpec.getTruncationSuffix();
        String sanitizedText = StringUtils.l(displayedText);
        if (sanitizedText.isEmpty()) {
            displayedText = sanitizedText;
        }
        double textHeight = fontRenderer.d("A");
        double textY = y + this.L() / 2.0 - textHeight / 2.0;
        double textX = x;
        if (this.centered) {
            textX += this.A() / 2.0;
        }
        if (this.drawShadow) {
            if (this.centered) {
                fontRenderer.f(displayedText, textX, textY, this.textColor);
            } else {
                fontRenderer.v(displayedText, textX, textY, this.textColor);
            }
        } else if (this.centered) {
            fontRenderer.W(displayedText, textX, textY, this.textColor);
        } else {
            fontRenderer.d(displayedText, textX, textY, this.textColor);
        }
        boolean textWasTruncated = truncationIndex < this.textSpec.getText().length() - 1;
        if (this.truncationTooltipEnabled && textWasTruncated) {
            String tooltipText = this.textSpec.getText() + (!this.additionalTooltipText.isEmpty() ? "\n" + this.additionalTooltipText : "");
            this.w(tooltipText);
            return;
        }
        this.w(!this.additionalTooltipText.isEmpty() ? this.additionalTooltipText : "");
    }

    public double getMaxWidth() {
        return this.textSpec.getMaxWidth();
    }

    public Color getTextColor() {
        return this.textColor;
    }

    public String getAdditionalTooltipText() {
        return this.additionalTooltipText;
    }

    public boolean isShadowEnabled() {
        return this.drawShadow;
    }

    public boolean isCentered() {
        return this.centered;
    }

    public boolean isTruncationTooltipEnabled() {
        return this.truncationTooltipEnabled;
    }


    public void setMaxWidth(double maxWidth) {
        this.textSpec.setMaxWidth(maxWidth);
    }

    public void setShadowEnabled(boolean drawShadow) {
        this.drawShadow = drawShadow;
    }

    public boolean isBold() {
        return this.textSpec.isBold();
    }

    public void setTruncationTooltipEnabled(boolean enabled) {
        this.truncationTooltipEnabled = enabled;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public double getRenderedWidth() {
        int truncationIndex = SuffixTextTruncationIndexCache.INSTANCE.getTruncationIndex(this.textSpec);
        SmoothFontRenderer fontRenderer = this.textSpec.isBold() ? this.getAlternateFontRenderer(this.textSpec.getFontScale()) : this.getFontRenderer(this.textSpec.getFontScale());
        if (truncationIndex == this.textSpec.getText().length() - 1) {
            return fontRenderer.N(this.getText());
        }
        if (truncationIndex == -1) {
            return fontRenderer.N(this.getTruncationSuffix());
        }
        if (truncationIndex == -2) {
            return 0.0;
        }
        return fontRenderer.N(this.getText().substring(0, truncationIndex) + this.getTruncationSuffix());
    }

    public void setTruncationSuffix(String truncationSuffix) {
        this.textSpec.setTruncationSuffix(truncationSuffix);
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public void setTextSpec(TruncatedTextSpec textSpec) {
        this.textSpec = textSpec;
    }

    public TruncatedTextComponent(String text, String truncationSuffix, double maxWidth, double fontScale) {
        this(text, truncationSuffix, maxWidth, fontScale, TruncatedTextComponent.J.A, false, false);
    }

    @Override
    public double C() {
        return this.getTextHeight();
    }

    public void setText(String text) {
        this.textSpec.setText(text);
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public double x() {
        return this.getRenderedWidth();
    }
}

