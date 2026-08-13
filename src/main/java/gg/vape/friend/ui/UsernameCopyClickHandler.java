package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.friend.ui.UsernameEditorPanel;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.notification.NotificationMessage;
import gg.vape.ui.notification.NotificationType;
import gg.vape.utils.ClipboardUtil;

public class UsernameCopyClickHandler
implements GuiClickListener {
    private final UsernameEditorPanel editorPanel;

    @Override
    public void onPrimaryClick() {
        ClipboardUtil.setText(Vape.INSTANCE.getOnlineManager().getLocalFriend().getDisplayName());
        OnlineFriendUiHelper.showNotification(new NotificationMessage(NotificationType.SUCCESS, "Copied " + Vape.INSTANCE.getOnlineManager().getLocalFriend().getDisplayName() + " to clipboard"));
    }

    public UsernameCopyClickHandler(UsernameEditorPanel usernameEditorPanel) {
        this.editorPanel = usernameEditorPanel;
    }
}
