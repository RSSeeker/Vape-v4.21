package gg.vape.ui.click.text;

import java.util.Objects;

public class TruncatedTextSpec {
    private boolean bold;
    private double fontScale;
    private double maxWidth;
    private String text;
    private String truncationSuffix;

    public double getMaxWidth() {
        return this.maxWidth;
    }

    public TruncatedTextSpec(String text, String truncationSuffix, double maxWidth, double fontScale, boolean bold) {
        this.text = text;
        this.truncationSuffix = truncationSuffix;
        this.maxWidth = maxWidth;
        this.fontScale = fontScale;
        this.bold = bold;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFontScale(double fontScale) {
        this.fontScale = fontScale;
    }

    public void setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public String getTruncationSuffix() {
        return this.truncationSuffix;
    }

    public String toString() {
        return "CutoffLabelData{text='" + this.text + '\'' + ", endText='" + this.truncationSuffix + '\'' + ", maxWidth=" + this.maxWidth + ", scale=" + this.fontScale + ", bold=" + this.bold + '}';
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setTruncationSuffix(String truncationSuffix) {
        this.truncationSuffix = truncationSuffix;
    }

    public int hashCode() {
        return Objects.hash(this.text, this.truncationSuffix, this.maxWidth, this.fontScale, this.bold);
    }

    public double getFontScale() {
        return this.fontScale;
    }

    public boolean isBold() {
        return this.bold;
    }

    public String getText() {
        return this.text;
    }
}
