package gg.vape.friend;

import gg.vape.Vape;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.PartyInvite;
import gg.vape.friend.PartyState;
import gg.vape.friend.ui.OnlineFriendsFrame;
import gg.vape.friend.ui.PartyInviteRow;
import gg.vape.module.none.ClientSettings;
import gg.vape.notification.NotificationType;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public class PartyManager {
    @Nullable
    private PartyState currentParty;
    private static final String INVITE_NOTIFICATION_TITLE;
    private static int obfuscationState;
    private final Map<OnlineFriend, PartyInvite> invites = new LinkedHashMap<OnlineFriend, PartyInvite>();

    public static void setObfuscationState(int state) {
        obfuscationState = state;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public PartyInvite getInvite(OnlineFriend onlineFriend) {
        Map<OnlineFriend, PartyInvite> map = this.invites;
        synchronized (map) {
            return this.invites.get(onlineFriend);
        }
    }

    public static int getObfuscationState() {
        return obfuscationState;
    }

    public static int getObfuscationConstant() {
        int state = PartyManager.getObfuscationState();
        return 0;
    }

    public void setCurrentParty(@Nullable PartyState partyState) {
        this.currentParty = partyState;
        ClientSettings.getFrame(OnlineFriendsFrame.class).l$src$V$1mibm4x();
        Vape.INSTANCE.getOnlineManager().getActivityManager().resetForWorldChange();
        Vape.INSTANCE.getOnlineManager().getInventoryTracker().reset();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeInvite(PartyInvite partyInvite) {
        Map<OnlineFriend, PartyInvite> map = this.invites;
        synchronized (map) {
            this.invites.remove(partyInvite.getInviter());
        }
        ClientSettings.getFrame(OnlineFriendsFrame.class).getPartyInvitesPanel().removeInvite(partyInvite);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addInvite(PartyInvite partyInvite) {
        Map<OnlineFriend, PartyInvite> map = this.invites;
        synchronized (map) {
            this.invites.put(partyInvite.getInviter(), partyInvite);
        }
        ClientSettings.getFrame(OnlineFriendsFrame.class).getPartyInvitesPanel().addInviteRow(new PartyInviteRow(partyInvite));
        Vape.INSTANCE.getNotificationManager().show(INVITE_NOTIFICATION_TITLE, partyInvite.getInviter().getDisplayName(), NotificationType.FRIENDS_PARTY_INVITE, 4000L);
    }

    static {
        PartyManager.setObfuscationState(117);
        INVITE_NOTIFICATION_TITLE = "Party invite";
    }

    @Nullable
    public PartyState getCurrentParty() {
        return this.currentParty;
    }


    public void clear() {
        this.setCurrentParty(null);
        for (PartyInvite partyInvite : this.invites.values()) {
            this.removeInvite(partyInvite);
        }
    }

    public @UnmodifiableView Collection<PartyInvite> getInvites() {
        return this.invites.values();
    }
}

