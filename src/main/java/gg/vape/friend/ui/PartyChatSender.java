package gg.vape.friend.ui;

import gg.vape.friend.OnlineFriend;
import gg.vape.friend.ui.OnlineChatSender;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.GroupChatResponsePacket;
import java.util.function.BiConsumer;

public class PartyChatSender
implements OnlineChatSender {
    @Override
    public void sendChatMessage(String message, BiConsumer<OnlineFriend, String> responseConsumer) {
        ZeusConnectionManager.T().u().L(message, PartyChatSender::handleChatResponse);
    }

    private static void handleChatResponse(GroupChatResponsePacket response) {
    }
}
