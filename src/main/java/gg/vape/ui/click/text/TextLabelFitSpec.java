package gg.vape.ui.click.text;

public class TextLabelFitSpec {
    private double maxWidth;
    private boolean bold;
    private double maxScale;
    private double scaleIncrement;
    private String text = "";
    private double minScale;

    public void setMinScale(double minScale) {
        this.minScale = minScale;
    }

    public double getMaxScale() {
        return this.maxScale;
    }

    public String toString() {
        return "ScalingLabelData{text='" + this.text + '\'' + ", minScale=" + this.minScale + ", maxScale=" + this.maxScale + ", scaleIncrement=" + this.scaleIncrement + ", maxWidth=" + this.maxWidth + ", bold=" + this.bold + '}';
    }

    public TextLabelFitSpec(String text, double minScale, double maxScale, double scaleIncrement, double maxWidth, boolean bold) {
        this.text = text;
        this.minScale = minScale;
        this.maxScale = maxScale;
        this.scaleIncrement = scaleIncrement;
        this.maxWidth = maxWidth;
        this.bold = bold;
    }

    public double getMaxWidth() {
        return this.maxWidth;
    }

    public double getScaleIncrement() {
        return this.scaleIncrement;
    }

    public String getText() {
        return this.text;
    }

    public double getMinScale() {
        return this.minScale;
    }

    public void setMaxScale(double maxScale) {
        this.maxScale = maxScale;
    }

    public boolean isBold() {
        return this.bold;
    }

    public void setScaleIncrement(double scaleIncrement) {
        this.scaleIncrement = scaleIncrement;
    }

    public void setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public void setText(String text) {
        this.text = text;
    }
}
