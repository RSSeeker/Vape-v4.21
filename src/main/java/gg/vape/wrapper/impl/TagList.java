package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MNBTTagList;
import gg.vape.wrapper.Wrapper;

public class TagList
extends Wrapper {
    public TagCompound getCompoundTagAt(int n) {
        return new TagCompound(MNBTTagList.w(TagList.vapeInstance.getMappingsMapperCompat().I, this.I, n));
    }

    public String a(int n) {
        return MNBTTagList.s(TagList.vapeInstance.getMappingsMapperCompat().I, this.I, n);
    }

    public int tagCount() {
        return MNBTTagList.R(TagList.vapeInstance.getMappingsMapperCompat().I, this.I);
    }

    public TagList(Object object) {
        super(object);
    }
}

