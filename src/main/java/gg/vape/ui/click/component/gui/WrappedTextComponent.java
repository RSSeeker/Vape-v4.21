package gg.vape.ui.click.component.gui;

import gg.vape.Vape;
import gg.vape.ui.click.component.SimpleTextLabelComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WrappedTextComponent
extends SimpleTextLabelComponent {
    private double measuredWidth;
    private boolean wrappingEnabled = true;
    private boolean bold;
    private double textOffsetX = 0.0;
    private double wrapWidth;
    private List<String> wrappedLines;
    private double measuredHeight = 0.0;
    private boolean centered = false;

    @Override
    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setWrapWidth(double wrapWidth) {
        this.wrapWidth = wrapWidth;
    }

    private void renderWrappedLines() {
        SmoothFontRenderer fontRenderer = this.bold ? Vape.INSTANCE.getFontManager().W(this.fontScale, false) : Vape.INSTANCE.getFontManager().E(this.fontScale, false);
        double currentY = this.n();
        for (String line : this.getWrappedLines()) {
            if (this.centered) {
                fontRenderer.v(line, this.G$src$D$1b2f02a() + this.textOffsetX, currentY, this.getTextColor());
            } else {
                fontRenderer.d(line, this.G$src$D$1b2f02a() + this.textOffsetX, currentY, this.getTextColor());
            }
            currentY += fontRenderer.d(line);
            double lineWidth = fontRenderer.N(line);
            if (!(lineWidth > this.measuredWidth)) continue;
            this.measuredWidth = lineWidth;
        }
        this.measuredHeight = currentY - this.n();
    }

    public List<String> getWrappedLines() {
        if (this.wrappedLines == null) {
            SmoothFontRenderer fontRenderer = this.bold ? Vape.INSTANCE.getFontManager().W(this.fontScale, false) : Vape.INSTANCE.getFontManager().E(this.fontScale, false);
            this.wrappedLines = this.wrapLines(Arrays.asList(this.text.split("\n")), fontRenderer);
        }
        return this.wrappedLines;
    }

    private ArrayList<String> wrapLines(List<String> sourceLines, SmoothFontRenderer fontRenderer) {
        ArrayList<String> wrappedResult = new ArrayList<String>();
        boolean splitOversizedWord = false;
        for (String sourceLine : sourceLines) {
            String[] words = sourceLine.split(" ");
            String currentLine = "";
            for (int wordIndex = 0; wordIndex < words.length; ++wordIndex) {
                String word = words[wordIndex];
                double wordWidth = fontRenderer.N(word);
                if (wordWidth > this.getWrapWidth()) {
                    splitOversizedWord = true;
                    double fittingRatio = this.getWrapWidth() / wordWidth;
                    int splitIndex = (int)((double)word.length() * fittingRatio);
                    String firstPart = word.substring(0, splitIndex);
                    String secondPart = word.substring(splitIndex, word.length() - 1);
                    wrappedResult.add(firstPart);
                    wrappedResult.add(secondPart);
                    continue;
                }
                if (wordIndex < words.length - 1) {
                    String nextWord = words[wordIndex + 1];
                    double nextWordWidth = fontRenderer.N(nextWord);
                    if (wordWidth + fontRenderer.N(currentLine) + nextWordWidth < this.getWrapWidth()) {
                        currentLine = currentLine + word + " ";
                        continue;
                    }
                    currentLine = currentLine + word;
                    currentLine = currentLine.trim();
                    wrappedResult.add(currentLine);
                    currentLine = "";
                    continue;
                }
                currentLine = currentLine + word;
                wrappedResult.add(currentLine);
            }
        }
        return splitOversizedWord ? this.wrapLines(wrappedResult, fontRenderer) : wrappedResult;
    }

    public void setWrappingEnabled(boolean wrappingEnabled) {
        this.wrappingEnabled = wrappingEnabled;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        this.wrappedLines = null;
    }

    @Override
    public void c() {
        super.c();
        this.renderWrappedLines();
    }

    public WrappedTextComponent(String text, double fontScale, Color color, boolean bold, double textOffsetX) {
        super(text, fontScale);
        this.setTextColor(color);
        this.bold = bold;
        this.textOffsetX = textOffsetX;
    }


    @Override
    public double A() {
        return this.measuredWidth;
    }

    @Override
    public double C() {
        return this.measuredHeight;
    }

    public WrappedTextComponent(String text, double fontScale, Color color, boolean bold) {
        super(text, fontScale);
        this.setTextColor(color);
        this.bold = bold;
    }

    @Override
    public void H() {
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
    }

    public double getWrapWidth() {
        return this.wrapWidth;
    }

    public WrappedTextComponent(String text, double fontScale) {
        super(text, fontScale);
    }
}

