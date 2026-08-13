package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMovementInput
extends Mapping {
    private MappingField y;
    private MappingField p;
    private MappingField q;
    private MappingField b;
    private MappingField K;
    private MappingField V;

    public MMovementInput() {
        this(BlockData.W());
    }

    private MMovementInput(String[] stringArray) {
        super(MappedClasses.qO);
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_21_6.d()) {
            Class clazz = MappedClasses.YG;
            boolean bl = true;
            String string = "moveVector";
            MMovementInput mMovementInput = this;
            this.q = mMovementInput.J(string, bl, clazz);
        } else {
            Class<Float> clazz = Float.TYPE;
            String string = "moveStrafe";
            MMovementInput mMovementInput = this;
            this.V = ((MappingFieldBuilder)mMovementInput.fieldBuilder(string, clazz).setNameForVersion(ForgeVersion.MC_1_21_0.n(), "leftImpulse")).buildField();
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            Class clazz = MappedClasses.qn;
            boolean bl = true;
            String string = "keyPresses";
            MMovementInput mMovementInput = this;
            this.y = mMovementInput.J(string, bl, clazz);
            if (ForgeVersion.MC_1_21_6.v()) {
                Class<Float> clazz2 = Float.TYPE;
                boolean bl2 = true;
                String string2 = "forwardImpulse";
                MMovementInput mMovementInput2 = this;
                this.p = this.J(string2, bl2, clazz2);
            }
        } else {
            Class<Boolean> clazz = Boolean.TYPE;
            String string = "jump";
            MMovementInput mMovementInput = this;
            this.K = ((MappingFieldBuilder)mMovementInput.fieldBuilder(string, clazz).setNameForVersion(ForgeVersion.MC_1_21_0.n(), "jumping")).buildField();
            Class<Boolean> clazz3 = Boolean.TYPE;
            String string3 = "sneak";
            MMovementInput mMovementInput3 = this;
            this.b = ((MappingFieldBuilder)((MappingFieldBuilder)this.fieldBuilder(string3, clazz3).setNameForVersion(ForgeVersion.MC_1_21_0.n(), "shiftKeyDown")).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "sneaking")).buildField();
            if (ForgeVersion.MC_1_12_2.d()) {
                if (ForgeVersion.MC_1_17.d()) {
                    Class<Float> clazz4 = Float.TYPE;
                    boolean bl = true;
                    String string4 = "forwardImpulse";
                    MMovementInput mMovementInput4 = this;
                    this.p = this.J(string4, bl, clazz4);
                } else {
                    Class<Float> clazz5 = Float.TYPE;
                    boolean bl = Wrapper.isNativeAvailable;
                    String string5 = "field_192832_b";
                    MMovementInput mMovementInput5 = this;
                    this.p = this.J(string5, bl, clazz5);
                }
            } else {
                Class<Float> clazz6 = Float.TYPE;
                boolean bl = true;
                String string6 = "moveForward";
                MMovementInput mMovementInput6 = this;
                this.p = this.J(string6, bl, clazz6);
            }
        }
    }

    private float P(Object object) {
        return this.V.getFloat(object);
    }

    private void C(Object object, Object object2) {
        this.y.setObject(object, object2);
    }

    private void F(Object object, Object object2) {
        this.q.setObject(object, object2);
    }

    public static boolean j(MMovementInput mMovementInput, Object object) {
        return mMovementInput.k(object);
    }

    public static void H(MMovementInput mMovementInput, Object object, boolean bl) {
        mMovementInput.l(object, bl);
    }

    public static Object W(MMovementInput mMovementInput, Object object) {
        return mMovementInput.O(object);
    }


    public static void x(MMovementInput mMovementInput, Object object, Object object2) {
        mMovementInput.C(object, object2);
    }

    private boolean k(Object object) {
        return this.K.getBoolean(object);
    }

    private void l(Object object, boolean bl) {
        this.K.setBoolean(object, bl);
    }

    public static void V(MMovementInput mMovementInput, Object object, boolean bl) {
        mMovementInput.t(object, bl);
    }

    private Object n(Object object) {
        return this.y.getObject(object);
    }

    private void G(Object object, float f) {
        this.p.setFloat(object, f);
    }

    private float f(Object object) {
        return this.p.getFloat(object);
    }

    public static void j(MMovementInput mMovementInput, Object object, float f) {
        mMovementInput.v(object, f);
    }

    private boolean T(Object object) {
        return this.b.getBoolean(object);
    }

    public static float f(MMovementInput mMovementInput, Object object) {
        return mMovementInput.f(object);
    }

    private Object O(Object object) {
        return this.q.getObject(object);
    }

    public static void p(MMovementInput mMovementInput, Object object, float f) {
        mMovementInput.G(object, f);
    }

    public static boolean R(MMovementInput mMovementInput, Object object) {
        return mMovementInput.T(object);
    }

    private void t(Object object, boolean bl) {
        this.b.setBoolean(object, bl);
    }

    public static float b(MMovementInput mMovementInput, Object object) {
        return mMovementInput.P(object);
    }

    private void v(Object object, float f) {
        this.V.setFloat(object, f);
    }

    public static Object x(MMovementInput mMovementInput, Object object) {
        return mMovementInput.n(object);
    }
}

