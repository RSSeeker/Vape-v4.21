package gg.vape.friend.ui;

import gg.vape.friend.ui.UsernameEditorPanel;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.TextLabelComponent;
import java.awt.Color;

class UsernameEditorCurrentNameLabel
extends TextLabelComponent {
    private final UsernameEditorPanel editorPanel;

    UsernameEditorCurrentNameLabel(UsernameEditorPanel usernameEditorPanel, String string, double d, double d2, double d3, double d4, boolean bl, boolean bl2, Color color, GuiComponent guiComponent) {
        super(string, d, d2, d3, d4, bl, bl2, color, guiComponent);
        this.editorPanel = usernameEditorPanel;
    }

    @Override
    public double C() {
        return 17.0;
    }
}
