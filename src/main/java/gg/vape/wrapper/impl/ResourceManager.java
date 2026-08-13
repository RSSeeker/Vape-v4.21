package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import java.util.Collection;

public class ResourceManager
extends Wrapper {
    public Collection<String> getSelectedIds() {
        return (Collection)ResourceManager.vapeInstance.getMappingsMapperCompat().packRepository.getSelectedIds(this.I);
    }

    public ResourceManager(Object handle) {
        super(handle);
    }
}
