package gg.vape.friend.ui;

import gg.vape.friend.ui.PartyMemberEntryMode;

class PartyMemberEntryModeSwitchMap {
    static final int[] MODE_MAPPINGS = new int[PartyMemberEntryMode.values().length];

    PartyMemberEntryModeSwitchMap() {
    }

    static {
        try {
            PartyMemberEntryModeSwitchMap.MODE_MAPPINGS[PartyMemberEntryMode.CURRENT_PARTY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PartyMemberEntryModeSwitchMap.MODE_MAPPINGS[PartyMemberEntryMode.INVITE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
