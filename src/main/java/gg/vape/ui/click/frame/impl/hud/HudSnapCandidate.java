package gg.vape.ui.click.frame.impl.hud;

import gg.vape.ui.click.frame.impl.hud.HudSnapEdge;
import java.util.EnumMap;
import java.util.Map;

public class HudSnapCandidate {
    private final Map<HudSnapEdge, Double> bounds = new EnumMap<HudSnapEdge, Double>(HudSnapEdge.class);
    private final HudSnapEdge sourceEdge;
    private final double offset;
    private final HudSnapEdge targetEdge;

    public HudSnapEdge getTargetEdge() {
        return this.targetEdge;
    }

    public Map<HudSnapEdge, Double> getBounds() {
        return this.bounds;
    }

    public HudSnapCandidate(double left, double top, double right, double bottom,
            HudSnapEdge sourceEdge, HudSnapEdge targetEdge, double offset) {
        this.bounds.put(HudSnapEdge.LEFT, left);
        this.bounds.put(HudSnapEdge.TOP, top);
        this.bounds.put(HudSnapEdge.RIGHT, right);
        this.bounds.put(HudSnapEdge.BOTTOM, bottom);
        this.sourceEdge = sourceEdge;
        this.targetEdge = targetEdge;
        this.offset = offset;
    }

    public double getWidth() {
        return this.bounds.get(HudSnapEdge.RIGHT) - this.bounds.get(HudSnapEdge.LEFT);
    }

    public double getOffset() {
        return this.offset;
    }

    public double getHeight() {
        return this.bounds.get(HudSnapEdge.BOTTOM) - this.bounds.get(HudSnapEdge.TOP);
    }

    public HudSnapEdge getSourceEdge() {
        return this.sourceEdge;
    }
}
