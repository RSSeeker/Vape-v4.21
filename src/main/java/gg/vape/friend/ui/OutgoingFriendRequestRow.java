package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.OutgoingFriendRequest;
import gg.vape.friend.ui.FriendRequestRow;
import gg.vape.friend.ui.OutgoingFriendRequestCancelClickHandler;
import gg.vape.ui.font.SmoothFontRenderer;

public class OutgoingFriendRequestRow
extends FriendRequestRow {
    private static final String STATUS_TEXT = "Requested";
    private final OutgoingFriendRequest request;

    @Override
    public void H() {
        String statusText = STATUS_TEXT;
        SmoothFontRenderer smoothFontRenderer = this.getAlternateFontRenderer(0.75);
        this.nameLabel.setMaxWidth(this.A() - 6.0 - this.getCloseButton().A() - smoothFontRenderer.N(statusText) - 7.0 - 2.0);
        super.H();
        smoothFontRenderer.d(statusText, this.G$src$D$1b2f02a() + this.A() - smoothFontRenderer.N(statusText) - this.getCloseButton().A() - 7.0, this.n() + (17.0 - smoothFontRenderer.d(statusText)) / 2.0, OutgoingFriendRequestRow.J.h);
    }

    public static void cancel(OutgoingFriendRequestRow row) {
        row.cancelRequest();
    }

    public OutgoingFriendRequestRow(OutgoingFriendRequest outgoingFriendRequest) {
        super(outgoingFriendRequest, null);
        this.request = outgoingFriendRequest;
        this.getCloseButton().addClickListener(new OutgoingFriendRequestCancelClickHandler(this));
    }

    private void cancelRequest() {
        Vape.INSTANCE.getOnlineManager().getFriendRequestManager().cancelOutgoingRequest(this.request);
    }
}
