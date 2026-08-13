package gg.vape.click;

public class ClickRegionBounds {
    private int lowerBound;
    private final ClickEngine clickEngine;
    private int upperBound;

    public ClickRegionBounds(ClickEngine clickEngine, int lowerBound, int upperBound) {
        this.clickEngine = clickEngine;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public int getLowerBound() {
        return this.lowerBound;
    }

    public int getUpperBound() {
        return this.upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public ClickEngine getClickEngine() {
        return this.clickEngine;
    }
}
