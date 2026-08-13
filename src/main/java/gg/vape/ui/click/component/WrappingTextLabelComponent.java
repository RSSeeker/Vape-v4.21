package gg.vape.ui.click.component;

import gg.vape.Vape;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;

public class WrappingTextLabelComponent
extends SimpleTextLabelComponent {
    private double wrapWidthOverride = -1.0;
    private double measuredTextHeight = 0.0;

    public double getWrapWidthOverride() {
        return this.wrapWidthOverride;
    }

    public double getMeasuredTextHeight() {
        return this.measuredTextHeight;
    }


    public void setWrapWidthOverride(double wrapWidthOverride) {
        this.wrapWidthOverride = wrapWidthOverride;
    }

    @Override
    public void H() {
        if (this.text.contains("\n")) {
            String[] paragraphs = this.text.split("\n");
            int accumulatedHeight = 0;
            for (String paragraph : paragraphs) {
                accumulatedHeight += this.renderParagraph(paragraph, accumulatedHeight);
            }
            this.measuredTextHeight = accumulatedHeight;
        } else {
            this.measuredTextHeight = this.renderParagraph(this.text, 0.0);
        }
    }

    @Override
    public double x() {
        return super.getExplicitWidth();
    }

    public WrappingTextLabelComponent(String text, double fontScale, Color color) {
        super(text, fontScale);
        this.setTextColor(color);
    }

    private int renderParagraph(String text, double yOffset) {
        SmoothFontRenderer fontRenderer = Vape.INSTANCE.getFontManager().W(this.fontScale, false);
        if (text.contains(" ")) {
            String[] words = text.split(" ");
            double currentLineWidth = 0.0;
            String currentLine = "";
            double lineCount = 0.0;
            double lineHeight = fontRenderer.d(text);
            for (String word : words) {
                double wordWidth = fontRenderer.N(word + " ");
                currentLineWidth += wordWidth;
                double wrapWidth = this.getWrapWidthOverride() != -1.0 ? this.getWrapWidthOverride() : this.A();
                if (currentLineWidth > wrapWidth) {
                    double renderedLineWidth = fontRenderer.N(currentLine);
                    double lineY = this.n() + yOffset + this.L() / 2.0 - lineHeight / 2.0;
                    double centerX = this.G$src$D$1b2f02a() + this.A() / 2.0;
                    double lineX = centerX - renderedLineWidth / 2.0;
                    fontRenderer.d(currentLine, lineX, lineY, this.getTextColor());
                    currentLine = "";
                    currentLineWidth = 0.0;
                    lineCount += 1.0;
                    yOffset += lineHeight;
                    currentLineWidth += wordWidth;
                }
                currentLine = currentLine + word + " ";
            }
            if (lineHeight == 0.0) {
                lineHeight = fontRenderer.d("|");
            }
            double renderedLineWidth = fontRenderer.N(currentLine);
            double lineY = this.n() + yOffset + this.L() / 2.0 - lineHeight / 2.0;
            double centerX = this.G$src$D$1b2f02a() + this.A() / 2.0;
            double lineX = centerX - renderedLineWidth / 2.0;
            fontRenderer.d(currentLine, lineX, lineY, this.getTextColor());
            return (int)(lineHeight * (lineCount += 1.0));
        }
        double textHeight = fontRenderer.d(text);
        double textWidth = fontRenderer.N(text);
        double textY = this.n() + yOffset + this.L() / 2.0 - textHeight / 2.0;
        double centerX = this.G$src$D$1b2f02a() + this.A() / 2.0;
        double textX = centerX - textWidth / 2.0;
        fontRenderer.d(text, textX, textY, this.getTextColor());
        return (int)textHeight;
    }

    public WrappingTextLabelComponent(String text, double fontScale) {
        super(text, fontScale);
    }
}

