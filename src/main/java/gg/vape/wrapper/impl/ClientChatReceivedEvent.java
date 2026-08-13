package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ClientChatReceivedEvent
extends Wrapper {
    public ClientChatReceivedEvent(Object object) {
        super(object);
    }

    public ITextComponent P() {
        return new ITextComponent(ClientChatReceivedEvent.vapeInstance.getMappingsMapperCompat().qP.V(this.I));
    }

    public void t(ITextComponent iTextComponent) {
        ClientChatReceivedEvent.vapeInstance.getMappingsMapperCompat().qP.i(this.I, iTextComponent.getObject());
    }
}

