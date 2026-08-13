package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Matrix4f;
import java.nio.FloatBuffer;

public class MMatrix4f
extends Mapping {
    private MappingMethod X;
    private MappingMethod M;
    private MappingMethod L;
    private MappingMethod z;
    private MappingMethod D;


    private Object O(Object object, float f, float f2, float f3) {
        return this.z.invokeObject(object, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3));
    }

    private void z(Object object, FloatBuffer floatBuffer) {
        this.X.invokeVoid(object, floatBuffer);
    }

    public static void l(MMatrix4f mMatrix4f, Object object, Matrix4f matrix4f) {
        mMatrix4f.e(object, matrix4f);
    }

    public static Object O(MMatrix4f mMatrix4f, Object object, float f, float f2, float f3) {
        return mMatrix4f.O(object, f, f2, f3);
    }

    private Object b() {
        return this.D.newInstance(new Object[0]);
    }

    private void O(Object object, FloatBuffer floatBuffer) {
        if (ForgeVersion.MC_1_20_6.d()) {
            this.M.invokeObject(object, floatBuffer);
            return;
        }
        this.M.invokeVoid(object, floatBuffer);
    }

    private void e(Object object, Matrix4f matrix4f) {
        if (ForgeVersion.MC_1_20_6.d()) {
            this.L.invokeObject(object, matrix4f.getObject());
            return;
        }
        this.L.invokeVoid(object, matrix4f.getObject());
    }

    public static void d(MMatrix4f mMatrix4f, Object object, FloatBuffer floatBuffer) {
        mMatrix4f.z(object, floatBuffer);
    }

    public static Object E(MMatrix4f mMatrix4f) {
        return mMatrix4f.b();
    }

    public static Object P(MMatrix4f mMatrix4f, FloatBuffer floatBuffer) {
        return mMatrix4f.Z(floatBuffer);
    }

    private Object Z(FloatBuffer floatBuffer) {
        return this.X.newInstance(floatBuffer);
    }

    public static void M(MMatrix4f mMatrix4f, Object object, FloatBuffer floatBuffer) {
        mMatrix4f.O(object, floatBuffer);
    }

    public MMatrix4f() {
        this(BlockData.W());
    }

    private MMatrix4f(String[] stringArray) {
        super(MappedClasses.qr);
        if (stringArray != null) {
            Class[] classArray = new Class[]{};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            String string = "<init>";
            MMatrix4f mMatrix4f = this;
            this.D = mMatrix4f.Y(string, bl, clazz, classArray);
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_20_6.d()) {
                    Class[] classArray2 = new Class[]{FloatBuffer.class};
                    Class<FloatBuffer> clazz2 = FloatBuffer.class;
                    boolean bl2 = false;
                    String string2 = "get";
                    MMatrix4f mMatrix4f2 = this;
                    this.M = this.Y(string2, bl2, clazz2, classArray2);
                    Class[] classArray3 = new Class[]{FloatBuffer.class};
                    Class<Void> clazz3 = Void.TYPE;
                    boolean bl3 = false;
                    String string3 = "<init>";
                    MMatrix4f mMatrix4f3 = this;
                    this.X = this.Y(string3, bl3, clazz3, classArray3);
                    Class[] classArray4 = new Class[]{MappedClasses.q2};
                    Class clazz4 = MappedClasses.qr;
                    boolean bl4 = false;
                    String string4 = "mul";
                    MMatrix4f mMatrix4f4 = this;
                    this.L = this.Y(string4, bl4, clazz4, classArray4);
                    Class[] classArray5 = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE};
                    Class clazz5 = MappedClasses.qr;
                    boolean bl5 = false;
                    String string5 = "scaling";
                    MMatrix4f mMatrix4f5 = this;
                    this.z = this.Y(string5, bl5, clazz5, classArray5);
                } else {
                    Class[] classArray6 = new Class[]{MappedClasses.qr};
                    Class<Void> clazz6 = Void.TYPE;
                    boolean bl6 = Wrapper.isNativeAvailable;
                    String string6 = "func_226595_a_";
                    MMatrix4f mMatrix4f6 = this;
                    this.L = this.Y(string6, bl6, clazz6, classArray6);
                }
            } else {
                Class[] classArray7 = new Class[]{FloatBuffer.class};
                Class<Void> clazz7 = Void.TYPE;
                boolean bl7 = true;
                String string7 = "write";
                MMatrix4f mMatrix4f7 = this;
                this.M = this.Y(string7, bl7, clazz7, classArray7);
            }
            return;
        }
        Class[] classArray = new Class[]{};
        Class<Void> clazz = Void.TYPE;
        boolean bl = false;
        String string = "<init>";
        MMatrix4f mMatrix4f = this;
        this.M = mMatrix4f.Y(string, bl, clazz, classArray);
    }
}

