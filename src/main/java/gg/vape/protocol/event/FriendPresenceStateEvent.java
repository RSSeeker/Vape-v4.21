package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.PresenceState;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEventPayloadBase;
import gg.vape.ui.click.component.GuiComponent;

public class FriendPresenceStateEvent
extends OnlineEventPayloadBase {
    private final PresenceState q;
    private static GuiComponent[] g;

    public PresenceState O() {
        return this.q;
    }

    public static GuiComponent[] a_up_arr_f() {
        return g;
    }

    public static void u(GuiComponent[] guiComponentArray) {
        g = guiComponentArray;
    }

    public FriendPresenceStateEvent(ZeusClient zeusClient, UserModel userModel, PresenceState presenceState) {
        super(zeusClient, userModel);
        this.q = presenceState;
    }

    static {
        if (FriendPresenceStateEvent.a_up_arr_f() == null) {
            FriendPresenceStateEvent.u(new GuiComponent[2]);
        }
    }
}

