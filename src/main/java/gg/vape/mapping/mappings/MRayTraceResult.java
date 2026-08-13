package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRayTraceResult
extends Mapping {
    private MappingMethod D;
    public final MappingField c;
    public MappingField I;
    private MappingMethod Z;
    private MappingMethod b;
    private MappingMethod d;
    public final MappingField E;
    public MappingField k;
    public MappingMethod F;
    public MappingField H;
    public MappingField a;
    public final MappingField N;
    public MappingField U;

    private Object M(Object object) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.F.invokeObject(object, new Object[0]);
        }
        return this.H.getObject(object);
    }

    public MRayTraceResult() {
        this(BlockData.W());
    }

    private MRayTraceResult(String[] stringArray) {
        super(MappedClasses.DT);
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_7_10.L()) {
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "blockX";
            MRayTraceResult mRayTraceResult = this;
            this.k = mRayTraceResult.J(string, bl, clazz);
            Class<Integer> clazz2 = Integer.TYPE;
            boolean bl2 = true;
            String string2 = "blockY";
            MRayTraceResult mRayTraceResult2 = this;
            this.a = this.J(string2, bl2, clazz2);
            Class<Integer> clazz3 = Integer.TYPE;
            boolean bl3 = true;
            String string3 = "blockZ";
            MRayTraceResult mRayTraceResult3 = this;
            this.U = this.J(string3, bl3, clazz3);
            Class<Integer> clazz4 = Integer.TYPE;
            boolean bl4 = true;
            String string4 = "sideHit";
            MRayTraceResult mRayTraceResult4 = this;
            this.c = this.J(string4, bl4, clazz4);
        } else if (ForgeVersion.MC_1_16_5.d()) {
            Class clazz = MappedClasses.lf;
            boolean bl = true;
            String string = "pos";
            Class clazz5 = MappedClasses.qF;
            MRayTraceResult mRayTraceResult = this;
            this.I = mRayTraceResult.registerInstanceFieldForOwner(clazz5, string, bl, clazz);
            Class clazz6 = MappedClasses.q0;
            boolean bl5 = true;
            String string5 = "face";
            Class clazz7 = MappedClasses.qF;
            MRayTraceResult mRayTraceResult5 = this;
            this.c = this.registerInstanceFieldForOwner(clazz7, string5, bl5, clazz6);
        } else {
            Class clazz = MappedClasses.lf;
            boolean bl = true;
            String string = "blockPos";
            MRayTraceResult mRayTraceResult = this;
            this.I = mRayTraceResult.J(string, bl, clazz);
            Class clazz8 = MappedClasses.q0;
            boolean bl6 = true;
            String string6 = "sideHit";
            MRayTraceResult mRayTraceResult6 = this;
            this.c = this.J(string6, bl6, clazz8);
        }
        if (ForgeVersion.MC_1_16_5.v()) {
            Class clazz = MappedClasses.qP;
            boolean bl = true;
            String string = "hitVec";
            MRayTraceResult mRayTraceResult = this;
            this.E = mRayTraceResult.J(string, bl, clazz);
            Class clazz9 = MappedClasses.zc;
            boolean bl7 = true;
            String string7 = "entityHit";
            MRayTraceResult mRayTraceResult7 = this;
            this.N = this.J(string7, bl7, clazz9);
            Class clazz10 = MappedClasses.lk;
            boolean bl8 = true;
            String string8 = "typeOfHit";
            MRayTraceResult mRayTraceResult8 = this;
            this.H = this.J(string8, bl8, clazz10);
            Class[] classArray = new Class[]{MappedClasses.zc, MappedClasses.qP};
            Class<Void> clazz11 = Void.TYPE;
            boolean bl9 = false;
            String string9 = "<init>";
            MRayTraceResult mRayTraceResult9 = this;
            this.D = this.Y(string9, bl9, clazz11, classArray);
            if (ForgeVersion.MC_1_7_10.Y()) {
                Class[] classArray2 = new Class[]{MappedClasses.lk, MappedClasses.qP, MappedClasses.q0, MappedClasses.lf};
                Class<Void> clazz12 = Void.TYPE;
                boolean bl10 = false;
                String string10 = "<init>";
                MRayTraceResult mRayTraceResult10 = this;
                this.Z = this.Y(string10, bl10, clazz12, classArray2);
            }
        } else {
            Class clazz = MappedClasses.qP;
            boolean bl = true;
            String string = "hitResult";
            MRayTraceResult mRayTraceResult = this;
            this.E = mRayTraceResult.J(string, bl, clazz);
            Class clazz13 = MappedClasses.zc;
            boolean bl11 = true;
            String string11 = "entity";
            Class clazz14 = MappedClasses.zl;
            MRayTraceResult mRayTraceResult11 = this;
            this.N = this.registerInstanceFieldForOwner(clazz14, string11, bl11, clazz13);
            Class[] classArray = new Class[]{};
            Class clazz15 = MappedClasses.lk;
            boolean bl12 = true;
            String string12 = "getType";
            MRayTraceResult mRayTraceResult12 = this;
            this.F = this.Y(string12, bl12, clazz15, classArray);
            Class[] classArray3 = new Class[]{MappedClasses.qP, MappedClasses.q0, MappedClasses.lf, Boolean.TYPE};
            Class<Void> clazz16 = Void.TYPE;
            boolean bl13 = false;
            String string13 = "<init>";
            Class clazz17 = MappedClasses.qF;
            MRayTraceResult mRayTraceResult13 = this;
            this.b = this.registerInstanceMethodForOwner(clazz17, string13, bl13, clazz16, classArray3);
            Class[] classArray4 = new Class[]{MappedClasses.zc, MappedClasses.qP};
            Class<Void> clazz18 = Void.TYPE;
            boolean bl14 = false;
            String string14 = "<init>";
            Class clazz19 = MappedClasses.zl;
            MRayTraceResult mRayTraceResult14 = this;
            this.d = this.registerInstanceMethodForOwner(clazz19, string14, bl14, clazz18, classArray4);
        }
    }

    public Object m(Object object, Object object2, Object object3, Object object4) {
        return this.Z.newInstance(object, object2, object3, object4);
    }

    public int n(Object object) {
        return this.c.getInt(object);
    }

    public Object y(Object object, Object object2) {
        return this.D.newInstance(object, object2);
    }

    public static Object Z(MRayTraceResult mRayTraceResult, Object object) {
        return mRayTraceResult.M(object);
    }

    public Object o(Object object, Object object2, Object object3, boolean bl) {
        return this.b.newInstance(object, object2, object3, bl);
    }

    public int z(Object object) {
        return this.U.getInt(object);
    }

    public Object M(Object object, Object object2) {
        return this.d.newInstance(object, object2);
    }

    public Object o(Object object) {
        return this.E.getObject(object);
    }

    public Object F(Object object) {
        return this.N.getObject(object);
    }

    public void a(Object object, Object object2) {
        this.N.setObject(object, object2);
    }

    public int a(Object object) {
        return this.k.getInt(object);
    }


    public Object D(Object object) {
        return this.I.getObject(object);
    }

    public Object P(Object object) {
        return this.c.getObject(object);
    }

    public int w(Object object) {
        return this.a.getInt(object);
    }
}

