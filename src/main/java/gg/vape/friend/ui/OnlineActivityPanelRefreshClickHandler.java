package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineActivitySettingsFrame;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import java.awt.Point;

public class OnlineActivityPanelRefreshClickHandler
implements GuiMouseListener {
    private final OnlineActivitySettingsFrame settingsFrame;

    public OnlineActivityPanelRefreshClickHandler(OnlineActivitySettingsFrame onlineActivitySettingsFrame) {
        this.settingsFrame = onlineActivitySettingsFrame;
    }

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        if (mouseClickButton != MouseClickButton.LEFT_CLICK) {
            return;
        }
        OnlineActivitySettingsFrame.getActivityListPanel(this.settingsFrame).requestRefresh();
    }

}

