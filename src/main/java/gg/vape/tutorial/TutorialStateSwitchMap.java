package gg.vape.tutorial;

import gg.vape.tutorial.TutorialState;

public class TutorialStateSwitchMap {
    public static final int[] D = new int[TutorialState.values().length];

    TutorialStateSwitchMap() {
    }

    static {
        try {
            TutorialStateSwitchMap.D[TutorialState.WELCOME.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            TutorialStateSwitchMap.D[TutorialState.COMPLETED_ALL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            TutorialStateSwitchMap.D[TutorialState.COMPLETED_TUTORIAL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

