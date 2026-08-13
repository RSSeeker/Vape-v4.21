package gg.vape.friend.ui;

import gg.vape.protocol.packet.GroupInviteStatus;

public class OnlineFriendActionPanelGroupInviteStatusSwitchMap {
    public static final int[] STATUS_MAPPINGS = new int[GroupInviteStatus.values().length];

    OnlineFriendActionPanelGroupInviteStatusSwitchMap() {
    }

    static {
        try {
            OnlineFriendActionPanelGroupInviteStatusSwitchMap.STATUS_MAPPINGS[GroupInviteStatus.SUCCESS.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OnlineFriendActionPanelGroupInviteStatusSwitchMap.STATUS_MAPPINGS[GroupInviteStatus.TOO_MANY_INVITES.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OnlineFriendActionPanelGroupInviteStatusSwitchMap.STATUS_MAPPINGS[GroupInviteStatus.NOT_ONLINE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OnlineFriendActionPanelGroupInviteStatusSwitchMap.STATUS_MAPPINGS[GroupInviteStatus.ALREADY_INVITED.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OnlineFriendActionPanelGroupInviteStatusSwitchMap.STATUS_MAPPINGS[GroupInviteStatus.FAILED.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
