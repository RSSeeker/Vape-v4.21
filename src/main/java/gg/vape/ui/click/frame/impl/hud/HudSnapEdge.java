package gg.vape.ui.click.frame.impl.hud;

import com.google.common.collect.Sets;
import func.skidline.RectData;
import java.util.Set;

public enum HudSnapEdge {
    TOP,
    BOTTOM,
    VERTICAL_CENTRE,
    LEFT,
    RIGHT,
    HORIZONTAL_CENTRE;

    private static final Set<HudSnapEdge> Y_EDGES;
    private static final Set<HudSnapEdge> X_EDGES;

    public static Set<HudSnapEdge> getYEdges() {
        return Y_EDGES;
    }

    static {
        Y_EDGES = Sets.newHashSet(TOP, BOTTOM, HORIZONTAL_CENTRE);
        X_EDGES = Sets.newHashSet(LEFT, RIGHT, VERTICAL_CENTRE);
    }

    public static Set<HudSnapEdge> getXEdges() {
        return X_EDGES;
    }


    public double getPosition(RectData bounds) {
        switch (this) {
            case LEFT: {
                return bounds.o();
            }
            case RIGHT: {
                return bounds.o() + (bounds.e() - 19.0);
            }
            case TOP: {
                return bounds.W();
            }
            case BOTTOM: {
                return bounds.W() + bounds.R() + 2.0;
            }
            case HORIZONTAL_CENTRE: {
                return bounds.W() + bounds.R() / 2.0;
            }
            case VERTICAL_CENTRE: {
                return bounds.o() + (bounds.e() - 20.0) / 2.0;
            }
        }
        return 0.0;
    }
}
