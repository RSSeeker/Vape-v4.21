package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MServerData;
import gg.vape.wrapper.Wrapper;

public class ServerData
extends Wrapper {
    public ServerData(Object wrappedObject) {
        super(wrappedObject);
    }

    public String getServerIp() {
        return MServerData.getServerIp(ServerData.vapeInstance.getMappingsMapperCompat().ht, this.I);
    }
}
