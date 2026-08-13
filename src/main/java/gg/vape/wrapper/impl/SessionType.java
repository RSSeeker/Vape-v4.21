package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class SessionType
extends Wrapper {
    public SessionType mojang() {
        return new SessionType(SessionType.vapeInstance.getMappingsMapperCompat().sessionType.getMojang());
    }

    public SessionType msa() {
        return new SessionType(SessionType.vapeInstance.getMappingsMapperCompat().sessionType.getMsa());
    }

    public SessionType legacy() {
        return new SessionType(SessionType.vapeInstance.getMappingsMapperCompat().sessionType.getLegacy());
    }

    public SessionType(Object wrappedObject) {
        super(wrappedObject);
    }
}
