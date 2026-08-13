package gg.vape.friend.ui;

import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.friend.ui.UsernameEditorPanel;
import gg.vape.ui.click.component.input.DebouncedTextInputComponent;
import gg.vape.ui.notification.NotificationMessage;
import gg.vape.ui.notification.NotificationType;

public class UsernameEditorTextInputComponent
extends DebouncedTextInputComponent {
    final UsernameEditorPanel editorPanel;

    @Override
    public float getVerticalInset() {
        return 0.0f;
    }

    private void handleNameChangeSuccess(String newName) {
        UsernameEditorPanel.toggleEditMode(this.editorPanel);
        OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.SUCCESS, "Name changed to " + newName));
    }

    @Override
    public float getRightInset() {
        return super.getRightInset();
    }

    @Override
    public double C() {
        return 16.0;
    }

    @Override
    public void handleSubmitReady() {
        UsernameEditorPanel.submitNameChange(UsernameEditorPanel.getRequestPending(this.editorPanel), this.getText(), this::handleNameChangeSuccess, UsernameEditorTextInputComponent::handleNameChangeError);
    }

    public UsernameEditorTextInputComponent(UsernameEditorPanel editorPanel, String text, long cooldownMillis) {
        super(text, cooldownMillis);
        this.editorPanel = editorPanel;
    }

    @Override
    public float getLeftInset() {
        return 0.0f;
    }

    @Override
    public void handleSubmitCooldown() {
        OnlineFriendUiHelper.showNotification(NotificationType.WARNING, "You are on cooldown!");
    }

    @Override
    public double x() {
        return 82.0;
    }

    private static void handleNameChangeError(String errorMessage) {
        OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.ERROR, errorMessage));
    }
}
