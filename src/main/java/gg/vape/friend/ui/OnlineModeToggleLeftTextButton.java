package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineModeToggleComponent;
import gg.vape.ui.click.component.gui.TextButton;
import java.awt.Color;

public class OnlineModeToggleLeftTextButton
extends TextButton {
    private final OnlineModeToggleComponent toggle;

    @Override
    public void H() {
    }

    public OnlineModeToggleLeftTextButton(OnlineModeToggleComponent onlineModeToggleComponent, String string, double d, Color color, Color color2) {
        super(string, d, color, color2);
        this.toggle = onlineModeToggleComponent;
    }
}
