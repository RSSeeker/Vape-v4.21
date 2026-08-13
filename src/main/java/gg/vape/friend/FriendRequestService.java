package gg.vape.friend;

import gg.vape.friend.ui.OnlineFriendUiHelper;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.FriendRequestResponsePacket;
import gg.vape.protocol.packet.FriendRequestResponseStatus;
import gg.vape.ui.notification.NotificationMessage;
import gg.vape.ui.notification.NotificationType;

public class FriendRequestService {
    public static void sendFriendRequest(String username) {
        ZeusConnectionManager.T().u().Z(username, FriendRequestService::lambda$sendFriendRequest$0);
    }

    private static void lambda$sendFriendRequest$0(FriendRequestResponsePacket friendRequestResponsePacket) {
        String responseMessage;
        NotificationType notificationType;
        if (friendRequestResponsePacket.getStatus() == FriendRequestResponseStatus.SENT) {
            notificationType = NotificationType.FRIENDINVITESENT;
            responseMessage = "Friend invite sent";
        } else if (friendRequestResponsePacket.getStatus() == FriendRequestResponseStatus.ALREADY_SENT) {
            notificationType = NotificationType.WARNING;
            responseMessage = "You've already sent a friend request to this person";
        } else if (friendRequestResponsePacket.getStatus() == FriendRequestResponseStatus.SELF_REQUEST) {
            notificationType = NotificationType.WARNING;
            responseMessage = "You cannot friend yourself";
        } else if (friendRequestResponsePacket.getStatus() == FriendRequestResponseStatus.ALREADY_FRIENDS) {
            notificationType = NotificationType.WARNING;
            responseMessage = "You're already friends";
        } else if (friendRequestResponsePacket.getStatus() == FriendRequestResponseStatus.INVALID_USER) {
            notificationType = NotificationType.WARNING;
            responseMessage = "Failed to find user";
        } else {
            notificationType = NotificationType.WARNING;
            responseMessage = "Unknown error";
        }
        OnlineFriendUiHelper.showNotification(new NotificationMessage(notificationType, responseMessage));
    }
}
