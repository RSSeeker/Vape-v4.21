package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.frame.impl.main.ClickGuiModuleViewMode;

public class ClickGuiModuleViewModeSwitchMap {
    public static final int[] C = new int[ClickGuiModuleViewMode.values().length];

    ClickGuiModuleViewModeSwitchMap() {
    }

    static {
        try {
            ClickGuiModuleViewModeSwitchMap.C[ClickGuiModuleViewMode.MACROS.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ClickGuiModuleViewModeSwitchMap.C[ClickGuiModuleViewMode.LEGIT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ClickGuiModuleViewModeSwitchMap.C[ClickGuiModuleViewMode.MODULE_CATEGORY.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

