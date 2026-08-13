package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MDataComponentMap;
import gg.vape.wrapper.Wrapper;

import java.util.Set;

public class DataComponentMap
extends Wrapper {
    public Object E(DataComponentType jr_12) {
        return DataComponentMap.vapeInstance.getMappingsMapperCompat().qk.j(this.I, jr_12.getObject());
    }

    public boolean V(DataComponentType jr_12) {
        return DataComponentMap.vapeInstance.getMappingsMapperCompat().qk.V(this.getObject(), jr_12.getObject());
    }

    public static DataComponentMap u() {
        return new DataComponentMap(MDataComponentMap.m(DataComponentMap.vapeInstance.getMappingsMapperCompat().qk));
    }

    public Set Z() {
        return (Set)MDataComponentMap.w(DataComponentMap.vapeInstance.getMappingsMapperCompat().qk, this.I);
    }

    public DataComponentMap(Object object) {
        super(object);
    }
}

