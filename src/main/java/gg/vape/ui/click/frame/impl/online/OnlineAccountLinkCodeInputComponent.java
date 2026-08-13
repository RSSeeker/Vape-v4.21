package gg.vape.ui.click.frame.impl.online;

import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.ui.click.component.input.DebouncedTextInputComponent;
import gg.vape.ui.click.frame.impl.online.OnlineAccountLinkCodePageComponent;
import gg.vape.ui.notification.NotificationType;

class OnlineAccountLinkCodeInputComponent
extends DebouncedTextInputComponent {
    final OnlineAccountLinkCodePageComponent linkCodePage;
    private static final String lb = "You are on cooldown!";

    @Override
    public void handleSubmitCooldown() {
        OnlineFriendUiHelper.showNotification(NotificationType.WARNING, lb);
    }

    OnlineAccountLinkCodeInputComponent(OnlineAccountLinkCodePageComponent linkCodePage, String text, long cooldownMillis) {
        super(text, cooldownMillis);
        this.linkCodePage = linkCodePage;
    }

    @Override
    public double C() {
        return 24.0;
    }

    @Override
    public double x() {
        return 104.0;
    }

    @Override
    public void handleSubmitReady() {
        OnlineAccountLinkCodePageComponent.r(this.linkCodePage);
    }
}
