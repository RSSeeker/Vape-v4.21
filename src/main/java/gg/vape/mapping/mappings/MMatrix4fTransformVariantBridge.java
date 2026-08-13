package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MMatrix4fTransformVariantBridge
extends Mapping {
    private MappingMethod i;
    private MappingMethod h;
    private MappingMethod V;
    private MappingMethod k;
    private MappingMethod c;
    private MappingMethod Q;

    private Object M(Object object) {
        return this.V.invokeObject(object, new Object[0]);
    }

    public static Object B(MMatrix4fTransformVariantBridge mMatrix4fTransformVariantBridge, Object object) {
        return mMatrix4fTransformVariantBridge.k(object);
    }

    public static Object Q(MMatrix4fTransformVariantBridge mMatrix4fTransformVariantBridge, Object object, float f, float f2, float f3) {
        return mMatrix4fTransformVariantBridge.T(object, f, f2, f3);
    }

    public static Object w(MMatrix4fTransformVariantBridge mMatrix4fTransformVariantBridge, Object object) {
        return mMatrix4fTransformVariantBridge.J(object);
    }

    private Object T(Object object, float f, float f2, float f3) {
        return this.c.invokeObject(object, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3));
    }

    public static Object D(MMatrix4fTransformVariantBridge mMatrix4fTransformVariantBridge, Object object) {
        return mMatrix4fTransformVariantBridge.M(object);
    }

    private Object J(Object object) {
        return this.i.invokeObject(object, new Object[0]);
    }

    public static Object L(MMatrix4fTransformVariantBridge mMatrix4fTransformVariantBridge, Object object, float f, float f2, float f3) {
        return mMatrix4fTransformVariantBridge.j(object, f, f2, f3);
    }

    private Object j(Object object, float f, float f2, float f3) {
        return this.k.invokeObject(object, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3));
    }

    public MMatrix4fTransformVariantBridge() {
        super(MappedClasses.VH);
        Class[] classArray = new Class[]{};
        Class clazz = MappedClasses.VH;
        boolean bl = false;
        String string = "pushMatrix";
        MMatrix4fTransformVariantBridge mMatrix4fTransformVariantBridge = this;
        this.V = this.Y(string, bl, clazz, classArray);
        Class[] classArray2 = new Class[]{};
        Class clazz2 = MappedClasses.VH;
        boolean bl2 = false;
        String string2 = "popMatrix";
        MMatrix4fTransformVariantBridge mMatrix4fTransformVariantBridge2 = this;
        this.Q = this.Y(string2, bl2, clazz2, classArray2);
        Class[] classArray3 = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE};
        Class clazz3 = MappedClasses.qr;
        boolean bl3 = false;
        String string3 = "translate";
        Class clazz4 = MappedClasses.qr;
        MMatrix4fTransformVariantBridge mMatrix4fTransformVariantBridge3 = this;
        this.c = this.registerInstanceMethodForOwner(clazz4, string3, bl3, clazz3, classArray3);
        Class[] classArray4 = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE};
        Class clazz5 = MappedClasses.qr;
        boolean bl4 = false;
        String string4 = "scale";
        Class clazz6 = MappedClasses.qr;
        MMatrix4fTransformVariantBridge mMatrix4fTransformVariantBridge4 = this;
        this.k = this.registerInstanceMethodForOwner(clazz6, string4, bl4, clazz5, classArray4);
        Class[] classArray5 = new Class[]{};
        Class clazz7 = MappedClasses.qr;
        boolean bl5 = false;
        String string5 = "identity";
        Class clazz8 = MappedClasses.qr;
        MMatrix4fTransformVariantBridge mMatrix4fTransformVariantBridge5 = this;
        this.i = this.registerInstanceMethodForOwner(clazz8, string5, bl5, clazz7, classArray5);
    }

    private Object k(Object object) {
        return this.Q.invokeObject(object, new Object[0]);
    }
}

