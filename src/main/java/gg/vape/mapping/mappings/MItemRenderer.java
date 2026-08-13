package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;

public class MItemRenderer
extends Mapping {
    private MappingMethod e;
    private MappingField o;
    public MappingMethod m;
    private MappingMethod g;
    private MappingField y;
    private MappingField q;
    public MappingMethod S;

    public static void K(MItemRenderer mItemRenderer, Object object, Object object2) {
        mItemRenderer.H(object, object2);
    }

    public void Z(Object object, Object object2, Object object3, Object object4) {
        this.g.invokeVoid(object, object2, object3, object4);
    }

    public float Y(Object object) {
        return this.y.getFloat(object);
    }

    public MItemRenderer() {
        super(MappedClasses.zN);
        Class[] classArray = new Class[]{Float.TYPE, Float.TYPE};
        Class<Void> clazz = Void.TYPE;
        boolean bl = true;
        String string = "transformFirstPersonItem";
        MItemRenderer mItemRenderer = this;
        this.m = this.Y(string, bl, clazz, classArray);
        Class[] classArray2 = new Class[]{Float.TYPE};
        Class<Void> clazz2 = Void.TYPE;
        boolean bl2 = true;
        String string2 = "renderItemInFirstPerson";
        MItemRenderer mItemRenderer2 = this;
        this.S = this.Y(string2, bl2, clazz2, classArray2);
        Class[] classArray3 = new Class[]{MappedClasses.zt};
        Class<Void> clazz3 = Void.TYPE;
        boolean bl3 = true;
        String string3 = "setLightMapFromPlayer";
        MItemRenderer mItemRenderer3 = this;
        this.e = this.Y(string3, bl3, clazz3, classArray3);
        Class[] classArray4 = new Class[]{MappedClasses.zm, MappedClasses.VK, MappedClasses.z0};
        Class<Void> clazz4 = Void.TYPE;
        boolean bl4 = true;
        String string4 = "renderItem";
        MItemRenderer mItemRenderer4 = this;
        this.g = this.Y(string4, bl4, clazz4, classArray4);
        Class<Float> clazz5 = Float.TYPE;
        boolean bl5 = true;
        String string5 = "prevEquippedProgress";
        MItemRenderer mItemRenderer5 = this;
        this.o = this.J(string5, bl5, clazz5);
        Class<Float> clazz6 = Float.TYPE;
        boolean bl6 = true;
        String string6 = "equippedProgress";
        MItemRenderer mItemRenderer6 = this;
        this.y = this.J(string6, bl6, clazz6);
        Class clazz7 = MappedClasses.VK;
        boolean bl7 = true;
        String string7 = "itemToRender";
        MItemRenderer mItemRenderer7 = this;
        this.q = this.J(string7, bl7, clazz7);
    }

    private void H(Object object, Object object2) {
        this.e.invokeVoid(object, object2);
    }

    public Object v(Object object) {
        return this.q.getObject(object);
    }

    public float S(Object object) {
        return this.o.getFloat(object);
    }
}

