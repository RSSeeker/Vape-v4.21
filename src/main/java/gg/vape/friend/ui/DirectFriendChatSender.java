package gg.vape.friend.ui;

import gg.vape.friend.OnlineFriend;
import gg.vape.friend.ui.OnlineChatSender;
import gg.vape.protocol.ZeusConnectionManager;
import gg.vape.protocol.packet.ChatToFriendResponsePacket;
import gg.vape.ui.click.component.GuiComponent;
import java.util.function.BiConsumer;

public class DirectFriendChatSender
implements OnlineChatSender {
    private final OnlineFriend friend;
    private static GuiComponent[] obfuscationComponents;

    private void handleChatResponse(BiConsumer<OnlineFriend, String> responseConsumer, ChatToFriendResponsePacket response) {
        responseConsumer.accept(this.friend, response.X());
    }

    public static void setObfuscationComponents(GuiComponent[] components) {
        obfuscationComponents = components;
    }

    @Override
    public void sendChatMessage(String message, BiConsumer<OnlineFriend, String> responseConsumer) {
        ZeusConnectionManager.T().u().p(this.friend.getUser(), message, response -> this.handleChatResponse(responseConsumer, (ChatToFriendResponsePacket)response));
    }

    public static GuiComponent[] getObfuscationComponents() {
        return obfuscationComponents;
    }

    public DirectFriendChatSender(OnlineFriend friend) {
        this.friend = friend;
    }

    static {
        if (DirectFriendChatSender.getObfuscationComponents() != null) {
            DirectFriendChatSender.setObfuscationComponents(new GuiComponent[3]);
        }
    }
}
