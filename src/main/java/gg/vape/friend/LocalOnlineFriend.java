package gg.vape.friend;

import gg.vape.account.MinecraftSessionWrapper;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.OnlineFriendActivityState;
import gg.vape.friend.UserModel;
import gg.vape.friend.ui.OnlineFriendActivityPanel;
import gg.vape.wrapper.impl.Minecraft;

public class LocalOnlineFriend
extends OnlineFriend {
    private final OnlineFriendActivityState activityState;
    private static final String DEFAULT_DISPLAY_NAME = "Self#1234";
    private final OnlineFriendActivityPanel activityPanel;

    public void setUser(UserModel userModel) {
        this.user = userModel;
    }

    public OnlineFriendActivityState getActivityState() {
        return this.activityState;
    }

    public OnlineFriendActivityPanel getActivityPanel() {
        return this.activityPanel;
    }

    public LocalOnlineFriend() {
        super(DEFAULT_DISPLAY_NAME);
        MinecraftSessionWrapper minecraftSessionWrapper = Minecraft.Q$src$Lgg_vape_account_MinecraftSessionWrapper_$1ftnn3u();
        this.updateMinecraftProfile(minecraftSessionWrapper.getProfileId(), minecraftSessionWrapper.getUsername());
        this.activityState = new OnlineFriendActivityState(this);
        this.activityPanel = new OnlineFriendActivityPanel(this);
    }
}
