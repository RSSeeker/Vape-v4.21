package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlinePlayerPreviewSettingsFrame;
import gg.vape.ui.click.component.ColorDividerComponent;
import java.awt.Color;

public class OnlinePlayerPreviewDividerComponent
extends ColorDividerComponent {
    final OnlinePlayerPreviewSettingsFrame settingsFrame;

    @Override
    public void I() {
    }

    public OnlinePlayerPreviewDividerComponent(OnlinePlayerPreviewSettingsFrame onlinePlayerPreviewSettingsFrame, Color color) {
        super(color);
        this.settingsFrame = onlinePlayerPreviewSettingsFrame;
    }
}
