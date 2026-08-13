package gg.vape.ui.click.layout;

import gg.vape.ui.click.layout.LayoutAnchor;
import gg.vape.ui.click.layout.LayoutDock;

class ComponentLayoutSwitchMap {
    static final int[] q = new int[LayoutDock.values().length];
    static final int[] p;

    ComponentLayoutSwitchMap() {
    }

    static {
        try {
            ComponentLayoutSwitchMap.q[LayoutDock.TOP.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ComponentLayoutSwitchMap.q[LayoutDock.BOTTOM.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ComponentLayoutSwitchMap.q[LayoutDock.LEFT.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ComponentLayoutSwitchMap.q[LayoutDock.RIGHT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        p = new int[LayoutAnchor.values().length];
        try {
            ComponentLayoutSwitchMap.p[LayoutAnchor.BOTTOM_RIGHT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ComponentLayoutSwitchMap.p[LayoutAnchor.TOP_LEFT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ComponentLayoutSwitchMap.p[LayoutAnchor.OFFSET.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

