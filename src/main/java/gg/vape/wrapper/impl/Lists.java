package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.List;

public class Lists
extends Wrapper {
    public Lists(Object listsHandle) {
        super(listsHandle);
    }

    public static ArrayList newArrayList(Iterable iterable) {
        return Lists.vapeInstance.getMappingsMapperCompat().lists.newArrayList(iterable);
    }

    public static List reverse(List list) {
        return Lists.vapeInstance.getMappingsMapperCompat().lists.reverse(list);
    }
}
