package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class PlayerEventNameFormat
extends Wrapper {
    public String getUrl() {
        return PlayerEventNameFormat.vapeInstance.getMappingsMapperCompat().resourcePackSendPacket.getUrl(this.I);
    }

    public void setUrl(String url) {
        PlayerEventNameFormat.vapeInstance.getMappingsMapperCompat().resourcePackSendPacket.setUrl(this.I, url);
    }

    public PlayerEventNameFormat(Object handle) {
        super(handle);
    }

    public String getHash() {
        return PlayerEventNameFormat.vapeInstance.getMappingsMapperCompat().resourcePackSendPacket.getHash(this.I);
    }
}
