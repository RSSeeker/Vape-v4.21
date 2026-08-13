package gg.vape.friend.ui;

import gg.vape.friend.OnlineFriend;
import java.util.function.BiConsumer;

public interface OnlineChatSender {
    void sendChatMessage(String message, BiConsumer<OnlineFriend, String> responseConsumer);
}
