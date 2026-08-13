package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.frame.impl.profile.ProfilePublishEditorPanel;
import java.awt.Point;

public class ProfilePublishEditorBooleanToggleClickHandler
implements GuiMouseListener {
    private final ProfilePublishEditorPanel editorPanel;

    @Override
    public void g(Point point, MouseClickButton mouseClickButton) {
        this.editorPanel.toggleFriendsOnlyVisibility();
    }


    public ProfilePublishEditorBooleanToggleClickHandler(ProfilePublishEditorPanel profilePublishEditorPanel) {
        this.editorPanel = profilePublishEditorPanel;
    }
}

