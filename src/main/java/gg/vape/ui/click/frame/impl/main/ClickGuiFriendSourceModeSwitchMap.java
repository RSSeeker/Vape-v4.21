package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.frame.impl.main.ClickGuiFriendSourceMode;

public class ClickGuiFriendSourceModeSwitchMap {
    public static final int[] Z = new int[ClickGuiFriendSourceMode.values().length];

    ClickGuiFriendSourceModeSwitchMap() {
    }

    static {
        try {
            ClickGuiFriendSourceModeSwitchMap.Z[ClickGuiFriendSourceMode.ONLINE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ClickGuiFriendSourceModeSwitchMap.Z[ClickGuiFriendSourceMode.MINECRAFT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

