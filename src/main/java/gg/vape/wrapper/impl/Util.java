package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class Util
extends Wrapper {
    public static long glfwGetCurrentContext() {
        return Util.vapeInstance.getMappingsMapperCompat().Rf.glfwGetCurrentContext();
    }

    public Util(Object wrappedObject) {
        super(wrappedObject);
    }

    public static void glfwPollEvents() {
        Util.vapeInstance.getMappingsMapperCompat().Rf.glfwPollEvents();
    }
}
