package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.ChatMessageRenderBridge;
import gg.vape.wrapper.impl.ITextComponent;

public class EventChatMessageRender
extends Event {
    private final Object messageSignature;
    private ChatMessageRenderBridge chatComponent;
    private Object outputContentComponent;
    private static final EventListeners EVENT_LISTENERS = new EventListeners();
    private final Object chatComponentHandle;
    private final Object contentComponentHandle;
    private final Object guiMessageTag;
    private ITextComponent contentComponent;

    public Object getMessageSignature() {
        return this.messageSignature;
    }


    public ITextComponent getContentComponent() {
        if (this.contentComponent == null) {
            this.contentComponent = new ITextComponent(this.contentComponentHandle);
        }
        return this.contentComponent;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public Object getOutputContentComponent() {
        return this.outputContentComponent;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public EventChatMessageRender(Object chatComponentHandle, Object contentComponentHandle, Object messageSignature, Object guiMessageTag) {
        this.chatComponentHandle = chatComponentHandle;
        this.contentComponentHandle = contentComponentHandle;
        this.messageSignature = messageSignature;
        this.guiMessageTag = guiMessageTag;
        this.outputContentComponent = contentComponentHandle;
    }

    public void setOutputContentComponent(ITextComponent contentComponent) {
        this.outputContentComponent = contentComponent.getObject();
    }

    public Object getGuiMessageTag() {
        return this.guiMessageTag;
    }

    public ChatMessageRenderBridge getChatComponent() {
        if (this.chatComponent == null) {
            this.chatComponent = new ChatMessageRenderBridge(this.chatComponentHandle);
        }
        return this.chatComponent;
    }
}

