package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MFreecamMovementInputBridge
extends Mapping {
    private MappingField A;
    private static GuiComponent[] n;
    private MappingField i;
    private MappingField o;
    private MappingField h;
    private MappingField V;
    private MappingField z;
    private MappingMethod l;
    private MappingField r;

    public void K(Object object, boolean bl) {
        this.A.setBoolean(object, bl);
    }

    public MFreecamMovementInputBridge() {
        super(MappedClasses.qn);
        Class<Boolean> clazz = Boolean.TYPE;
        boolean bl = true;
        String string = "forward";
        MFreecamMovementInputBridge mFreecamMovementInputBridge = this;
        this.z = this.J(string, bl, clazz);
        Class<Boolean> clazz2 = Boolean.TYPE;
        boolean bl2 = true;
        String string2 = "backward";
        MFreecamMovementInputBridge mFreecamMovementInputBridge2 = this;
        this.r = this.J(string2, bl2, clazz2);
        Class<Boolean> clazz3 = Boolean.TYPE;
        boolean bl3 = true;
        String string3 = "left";
        MFreecamMovementInputBridge mFreecamMovementInputBridge3 = this;
        this.V = this.J(string3, bl3, clazz3);
        Class<Boolean> clazz4 = Boolean.TYPE;
        boolean bl4 = true;
        String string4 = "right";
        MFreecamMovementInputBridge mFreecamMovementInputBridge4 = this;
        this.i = this.J(string4, bl4, clazz4);
        Class<Boolean> clazz5 = Boolean.TYPE;
        boolean bl5 = true;
        String string5 = "jump";
        MFreecamMovementInputBridge mFreecamMovementInputBridge5 = this;
        this.h = this.J(string5, bl5, clazz5);
        GuiComponent[] guiComponentArray = MFreecamMovementInputBridge.x();
        Class<Boolean> clazz6 = Boolean.TYPE;
        boolean bl6 = true;
        String string6 = "shift";
        MFreecamMovementInputBridge mFreecamMovementInputBridge6 = this;
        this.o = this.J(string6, bl6, clazz6);
        Class<Boolean> clazz7 = Boolean.TYPE;
        boolean bl7 = true;
        String string7 = "sprint";
        MFreecamMovementInputBridge mFreecamMovementInputBridge7 = this;
        this.A = this.J(string7, bl7, clazz7);
        Class[] classArray = new Class[]{Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE};
        MFreecamMovementInputBridge mFreecamMovementInputBridge8 = this;
        this.l = this.registerConstructor(classArray);
    }

    public boolean Y(Object object) {
        return this.A.getBoolean(object);
    }

    public boolean r(Object object) {
        return this.o.getBoolean(object);
    }

    public static GuiComponent[] x() {
        return n;
    }

    public void V(Object object, boolean bl) {
        this.o.setBoolean(object, bl);
    }

    public static void B(GuiComponent[] guiComponentArray) {
        n = guiComponentArray;
    }

    public void A(Object object, boolean bl) {
        this.h.setBoolean(object, bl);
    }

    static {
        MFreecamMovementInputBridge.B(new GuiComponent[3]);
    }

    public boolean A(Object object) {
        return this.i.getBoolean(object);
    }

    public boolean H(Object object) {
        return this.z.getBoolean(object);
    }

    public boolean K(Object object) {
        return this.V.getBoolean(object);
    }

    public boolean W(Object object) {
        return this.r.getBoolean(object);
    }

    public Object z(boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        return this.l.newInstance(bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }

    public boolean p(Object object) {
        return this.h.getBoolean(object);
    }

}

