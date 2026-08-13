package gg.vape.friend.ui;

import gg.vape.friend.ui.UsernameEditorPanel;
import gg.vape.ui.click.component.GuiClickListener;

public class UsernameEditorEditModeToggleClickHandler
implements GuiClickListener {
    private final UsernameEditorPanel editorPanel;

    public UsernameEditorEditModeToggleClickHandler(UsernameEditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    @Override
    public void onPrimaryClick() {
        UsernameEditorPanel.toggleEditMode(this.editorPanel);
    }
}
