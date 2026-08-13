package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MMainWindow;
import gg.vape.wrapper.Wrapper;

public class MainWindow
extends Wrapper {
    public MainWindow(Object object) {
        super(object);
    }

    public float i() {
        return MMainWindow.E(MainWindow.vapeInstance.getMappingsMapperCompat().DU, this.I);
    }

    public float q() {
        return MMainWindow.y(MainWindow.vapeInstance.getMappingsMapperCompat().DU, this.I);
    }

    public int x() {
        return MMainWindow.T(MainWindow.vapeInstance.getMappingsMapperCompat().DU, this.I);
    }

    public int o() {
        return MMainWindow.C(MainWindow.vapeInstance.getMappingsMapperCompat().DU, this.I);
    }

    public float r() {
        return MMainWindow.e(MainWindow.vapeInstance.getMappingsMapperCompat().DU, this.I);
    }

    public float A() {
        return MMainWindow.W(MainWindow.vapeInstance.getMappingsMapperCompat().DU, this.I);
    }
}

