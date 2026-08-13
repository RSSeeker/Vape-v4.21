package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.frame.impl.main.ClickGuiSection;

class ClickGuiSectionSwitchMap {
    static final int[] y = new int[ClickGuiSection.values().length];

    ClickGuiSectionSwitchMap() {
    }

    static {
        try {
            ClickGuiSectionSwitchMap.y[ClickGuiSection.MODULES.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ClickGuiSectionSwitchMap.y[ClickGuiSection.FRIENDS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ClickGuiSectionSwitchMap.y[ClickGuiSection.PROFILES.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

