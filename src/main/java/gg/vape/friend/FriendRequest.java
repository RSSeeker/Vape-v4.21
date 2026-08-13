package gg.vape.friend;

import gg.vape.Vape;
import gg.vape.friend.FriendRequestModel;
import gg.vape.friend.OnlineFriend;
import gg.vape.friend.UserModel;

public class FriendRequest {
    private final OnlineFriend friend;
    private static String[] obfuscationState;
    private final long id;

    public FriendRequest(FriendRequestModel model, boolean incoming) {
        this.id = model.getId();
        UserModel remoteUser = incoming ? model.getRequester() : model.getRecipient();
        this.friend = Vape.INSTANCE.getOnlineManager().getFriendCache().getOrCreateFriend(remoteUser.getId(), () -> new OnlineFriend(remoteUser));
    }

    static {
        if (FriendRequest.getObfuscationState() != null) {
            FriendRequest.setObfuscationState(new String[4]);
        }
    }

    public static void setObfuscationState(String[] state) {
        obfuscationState = state;
    }

    public long getId() {
        return this.id;
    }

    public static String[] getObfuscationState() {
        return obfuscationState;
    }

    public OnlineFriend getFriend() {
        return this.friend;
    }
}
