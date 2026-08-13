package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.value.ColorChannelType;

class ColorChannelTypeSwitchMap {
    static final int[] CHANNEL_CASES = new int[ColorChannelType.values().length];

    ColorChannelTypeSwitchMap() {
    }

    static {
        try {
            ColorChannelTypeSwitchMap.CHANNEL_CASES[ColorChannelType.BLOCK_CHILD.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ColorChannelTypeSwitchMap.CHANNEL_CASES[ColorChannelType.RAINBOW.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ColorChannelTypeSwitchMap.CHANNEL_CASES[ColorChannelType.SATURATION.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ColorChannelTypeSwitchMap.CHANNEL_CASES[ColorChannelType.VIBRANCE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ColorChannelTypeSwitchMap.CHANNEL_CASES[ColorChannelType.OPACITY.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
