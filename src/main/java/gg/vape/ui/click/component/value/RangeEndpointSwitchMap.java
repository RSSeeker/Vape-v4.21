package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.value.RangeEndpoint;

class RangeEndpointSwitchMap {
    static final int[] ENDPOINT_CASES = new int[RangeEndpoint.values().length];

    RangeEndpointSwitchMap() {
    }

    static {
        try {
            RangeEndpointSwitchMap.ENDPOINT_CASES[RangeEndpoint.MINIMUM.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RangeEndpointSwitchMap.ENDPOINT_CASES[RangeEndpoint.MAXIMUM.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
