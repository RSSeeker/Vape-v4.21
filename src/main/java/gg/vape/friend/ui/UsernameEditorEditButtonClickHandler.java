package gg.vape.friend.ui;

import gg.vape.friend.ui.UsernameEditorPanel;
import gg.vape.ui.click.component.GuiClickListener;

public class UsernameEditorEditButtonClickHandler
implements GuiClickListener {
    private final UsernameEditorPanel editorPanel;

    @Override
    public void onPrimaryClick() {
        UsernameEditorPanel.toggleEditMode(this.editorPanel);
    }

    public UsernameEditorEditButtonClickHandler(UsernameEditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }
}
