package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineFriendActionPanel;
import gg.vape.value.BooleanValue;

class OnlineFriendNotificationsValue
extends BooleanValue {
    private final OnlineFriendActionPanel actionPanel;

    @Override
    public Boolean getEffectiveValue() {
        if (OnlineFriendActionPanel.getFriend(this.actionPanel) == null) {
            return false;
        }
        return OnlineFriendActionPanel.getFriend(this.actionPanel).isSyncWithFriends();
    }

    public void P(Boolean value) {
        super.setValue(value);
        OnlineFriendActionPanel.getFriend(this.actionPanel).setSyncWithFriends(value);
    }


    OnlineFriendNotificationsValue(OnlineFriendActionPanel actionPanel, Object owner, String name, boolean defaultValue) {
        super(owner, name, defaultValue);
        this.actionPanel = actionPanel;
    }
}
