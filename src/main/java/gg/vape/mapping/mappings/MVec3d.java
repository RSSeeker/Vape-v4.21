package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MVec3d
extends Mapping {
    private final MappingField i;
    private final MappingField Q;
    public final MappingMethod k;
    private MappingMethod l;
    private final MappingMethod J;
    private MappingMethod a;
    private final MappingMethod Z;
    private MappingField S;
    private final MappingMethod U;
    private final MappingField m;

    public Object q(double d, double d2, double d3) {
        return this.k.newInstance(d, d2, d3);
    }

    public double z(Object object) {
        return this.Q.getDouble(object);
    }

    public Object M(Object object, Object object2, double d) {
        return this.l.invokeObject(object, object2, d);
    }

    public double P(Object object) {
        return this.m.getDouble(object);
    }

    public void K(Object object, double d) {
        this.Q.setDouble(object, d);
    }

    public MVec3d() {
        this(BlockData.W());
    }

    private MVec3d(String[] stringArray) {
        super(MappedClasses.qP);
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_16_5.d()) {
            Class<Double> clazz = Double.TYPE;
            boolean bl = true;
            String string = "x";
            MVec3d mVec3d = this;
            this.i = mVec3d.J(string, bl, clazz);
            Class<Double> clazz2 = Double.TYPE;
            boolean bl2 = true;
            String string2 = "y";
            MVec3d mVec3d2 = this;
            this.Q = this.J(string2, bl2, clazz2);
            Class<Double> clazz3 = Double.TYPE;
            boolean bl3 = true;
            String string3 = "z";
            MVec3d mVec3d3 = this;
            this.m = this.J(string3, bl3, clazz3);
            Class[] classArray = new Class[]{Double.TYPE};
            Class clazz4 = MappedClasses.qP;
            boolean bl4 = true;
            String string4 = "scale";
            MVec3d mVec3d4 = this;
            this.a = this.Y(string4, bl4, clazz4, classArray);
            if (ForgeVersion.MC_1_17.d()) {
                Class[] classArray2 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
                Class clazz5 = MappedClasses.qP;
                boolean bl5 = true;
                String string5 = "add";
                MVec3d mVec3d5 = this;
                this.Z = this.Y(string5, bl5, clazz5, classArray2);
            } else {
                Class[] classArray3 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
                Class clazz6 = MappedClasses.qP;
                boolean bl6 = Wrapper.isNativeAvailable;
                String string6 = "func_72441_c";
                MVec3d mVec3d6 = this;
                this.Z = this.Y(string6, bl6, clazz6, classArray3);
            }
            if (ForgeVersion.MC_1_21_4.d()) {
                Class[] classArray4 = new Class[]{MappedClasses.u9, Double.TYPE};
                Class clazz7 = MappedClasses.qP;
                String string7 = "with";
                MVec3d mVec3d7 = this;
                this.l = this.methodBuilder(string7, clazz7, classArray4).buildMethod();
                Class clazz8 = MappedClasses.qP;
                String string8 = "ZERO";
                MVec3d mVec3d8 = this;
                this.S = ((MappingFieldBuilder)this.fieldBuilder(string8, clazz8).setStaticMember(true)).buildField();
            }
        } else {
            Class<Double> clazz = Double.TYPE;
            boolean bl = true;
            String string = ForgeVersion.c() >= 23 ? "x" : "xCoord";
            MVec3d mVec3d = this;
            this.i = mVec3d.J(string, bl, clazz);
            Class<Double> clazz9 = Double.TYPE;
            boolean bl7 = true;
            String string9 = ForgeVersion.c() >= 23 ? "y" : "yCoord";
            MVec3d mVec3d9 = this;
            this.Q = this.J(string9, bl7, clazz9);
            Class<Double> clazz10 = Double.TYPE;
            boolean bl8 = true;
            String string10 = ForgeVersion.c() >= 23 ? "z" : "zCoord";
            MVec3d mVec3d10 = this;
            this.m = this.J(string10, bl8, clazz10);
            Class[] classArray = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
            Class clazz11 = MappedClasses.qP;
            boolean bl9 = true;
            String string11 = ForgeVersion.c() >= 23 ? "add" : "addVector";
            MVec3d mVec3d11 = this;
            this.Z = this.Y(string11, bl9, clazz11, classArray);
        }
        Class[] classArray = new Class[]{MappedClasses.qP};
        Class<Double> clazz = Double.TYPE;
        boolean bl = true;
        String string = "distanceTo";
        MVec3d mVec3d = this;
        this.J = mVec3d.Y(string, bl, clazz, classArray);
        if (ForgeVersion.MC_1_12_2.d()) {
            Class[] classArray5 = new Class[]{MappedClasses.qP};
            Class<Double> clazz12 = Double.TYPE;
            boolean bl10 = Wrapper.isNativeAvailable;
            String string12 = "func_72436_e";
            MVec3d mVec3d12 = this;
            this.U = this.Y(string12, bl10, clazz12, classArray5);
        } else {
            Class[] classArray6 = new Class[]{MappedClasses.qP};
            Class<Double> clazz13 = Double.TYPE;
            boolean bl11 = true;
            String string13 = "squareDistanceTo";
            MVec3d mVec3d13 = this;
            this.U = this.Y(string13, bl11, clazz13, classArray6);
        }
        Class[] classArray7 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
        Class<Void> clazz14 = Void.TYPE;
        boolean bl12 = false;
        String string14 = "<init>";
        MVec3d mVec3d14 = this;
        this.k = this.Y(string14, bl12, clazz14, classArray7);
    }

    public void Z(Object object, double d) {
        this.m.setDouble(object, d);
    }

    public Object H(Object object, double d) {
        return this.a.invokeObject(object, d);
    }

    public double S(Object object, Object object2) {
        return this.J.invokeDouble(object, object2);
    }

    public double O(Object object) {
        return this.i.getDouble(object);
    }


    public void V(Object object, double d) {
        this.i.setDouble(object, d);
    }

    public Object O() {
        return this.S.getObject(null);
    }

    public double A(Object object, Object object2) {
        return this.U.invokeDouble(object, object2);
    }

    public Object Y(Object object, double d, double d2, double d3) {
        return this.Z.invokeObject(object, d, d2, d3);
    }
}

