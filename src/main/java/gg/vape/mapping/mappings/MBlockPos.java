package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MBlockPos
extends Mapping {
    private final MappingMethod O;
    private MappingMethod T;
    private final MappingMethod F;
    private final MappingMethod a;
    private final MappingMethod Y;
    private final MappingMethod V;
    private final MappingMethod Q;
    private final MappingMethod m;
    public final MappingMethod E;
    private final MappingMethod x;

    public static Object s(MBlockPos mBlockPos, Object object) {
        return mBlockPos.o(object);
    }

    public Object H(double d, double d2, double d3) {
        return this.T.newInstance(d, d2, d3);
    }

    private Object V(Object object) {
        return this.m.invokeObject(object, new Object[0]);
    }

    public static Object b(MBlockPos mBlockPos, Object object) {
        return mBlockPos.p(object);
    }

    public static Object z(MBlockPos mBlockPos, Object object) {
        return mBlockPos.T(object);
    }

    private Object p(Object object) {
        return this.F.invokeObject(object, new Object[0]);
    }

    public Object offset(Object object, Object object2, int n) {
        return this.a.invokeObject(object, object2, n);
    }

    public Object D(Object object) {
        return this.V.newInstance(object);
    }

    public static Object H(MBlockPos mBlockPos, Object object) {
        return mBlockPos.S(object);
    }

    private Object T(Object object) {
        return this.Y.invokeObject(object, new Object[0]);
    }


    public MBlockPos() {
        this(BlockData.W());
    }

    private MBlockPos(String[] stringArray) {
        super(MappedClasses.lf);
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_20_6.d()) {
            Class[] classArray = new Class[]{MappedClasses.FD};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            String string = "<init>";
            MBlockPos mBlockPos = this;
            this.V = mBlockPos.Y(string, bl, clazz, classArray);
        } else {
            Class[] classArray = new Class[]{MappedClasses.qP};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            String string = "<init>";
            MBlockPos mBlockPos = this;
            this.V = mBlockPos.Y(string, bl, clazz, classArray);
        }
        Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE};
        Class<Void> clazz = Void.TYPE;
        boolean bl = false;
        String string = "<init>";
        MBlockPos mBlockPos = this;
        this.E = mBlockPos.Y(string, bl, clazz, classArray); 
        if (ForgeVersion.MC_1_20_6.v()) {
            Class[] classArray2 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl2 = false;
            String string2 = "<init>";
            MBlockPos mBlockPos2 = this;
            this.T = this.Y(string2, bl2, clazz2, classArray2);
        }
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            Class[] classArray3 = new Class[]{};
            Class clazz3 = MappedClasses.lf;
            boolean bl3 = true;
            String string3 = "up";
            MBlockPos mBlockPos3 = this;
            this.Q = this.Y(string3, bl3, clazz3, classArray3);
            Class[] classArray4 = new Class[]{};
            Class clazz4 = MappedClasses.lf;
            boolean bl4 = true;
            String string4 = "down";
            MBlockPos mBlockPos4 = this;
            this.Y = this.Y(string4, bl4, clazz4, classArray4);
            Class[] classArray5 = new Class[]{};
            Class clazz5 = MappedClasses.lf;
            boolean bl5 = true;
            String string5 = "north";
            MBlockPos mBlockPos5 = this;
            this.x = this.Y(string5, bl5, clazz5, classArray5);
            Class[] classArray6 = new Class[]{};
            Class clazz6 = MappedClasses.lf;
            boolean bl6 = true;
            String string6 = "east";
            MBlockPos mBlockPos6 = this;
            this.F = this.Y(string6, bl6, clazz6, classArray6);
            Class[] classArray7 = new Class[]{};
            Class clazz7 = MappedClasses.lf;
            boolean bl7 = true;
            String string7 = "south";
            MBlockPos mBlockPos7 = this;
            this.O = this.Y(string7, bl7, clazz7, classArray7);
            Class[] classArray8 = new Class[]{};
            Class clazz8 = MappedClasses.lf;
            boolean bl8 = true;
            String string8 = "west";
            MBlockPos mBlockPos8 = this;
            this.m = this.Y(string8, bl8, clazz8, classArray8);
        } else {
            if (ForgeVersion.MC_1_17.d()) {
                Class[] classArray9 = new Class[]{};
                Class clazz9 = MappedClasses.lf;
                boolean bl9 = false;
                String string9 = "m_7495_";
                MBlockPos mBlockPos9 = this;
                this.Y = this.Y(string9, bl9, clazz9, classArray9);
            } else {
                Class[] classArray10 = new Class[]{};
                Class clazz10 = MappedClasses.lf;
                boolean bl10 = Wrapper.isNativeAvailable;
                String string10 = "func_177977_b";
                MBlockPos mBlockPos10 = this;
                this.Y = this.Y(string10, bl10, clazz10, classArray10);
            }
            Class[] classArray11 = new Class[]{};
            Class clazz11 = MappedClasses.lf;
            boolean bl11 = Wrapper.isNativeAvailable && ForgeVersion.MC_1_20_6.v();
            String string11 = "func_177984_a";
            MBlockPos mBlockPos11 = this;
            this.Q = this.Y(string11, bl11, clazz11, classArray11);
            Class[] classArray12 = new Class[]{};
            Class clazz12 = MappedClasses.lf;
            boolean bl12 = Wrapper.isNativeAvailable && ForgeVersion.MC_1_20_6.v();
            String string12 = "func_177978_c";
            MBlockPos mBlockPos12 = this;
            this.x = this.Y(string12, bl12, clazz12, classArray12);
            Class[] classArray13 = new Class[]{};
            Class clazz13 = MappedClasses.lf;
            boolean bl13 = Wrapper.isNativeAvailable && ForgeVersion.MC_1_20_6.v();
            String string13 = "func_177974_f";
            MBlockPos mBlockPos13 = this;
            this.F = this.Y(string13, bl13, clazz13, classArray13);
            Class[] classArray14 = new Class[]{};
            Class clazz14 = MappedClasses.lf;
            boolean bl14 = Wrapper.isNativeAvailable && ForgeVersion.MC_1_20_6.v();
            String string14 = "func_177968_d";
            MBlockPos mBlockPos14 = this;
            this.O = this.Y(string14, bl14, clazz14, classArray14);
            Class[] classArray15 = new Class[]{};
            Class clazz15 = MappedClasses.lf;
            boolean bl15 = Wrapper.isNativeAvailable && ForgeVersion.MC_1_20_6.v();
            String string15 = "func_177976_e";
            MBlockPos mBlockPos15 = this;
            this.m = this.Y(string15, bl15, clazz15, classArray15);
        }
        if (ForgeVersion.MC_1_16_5.d() && ForgeVersion.MC_1_20_6.v() && Wrapper.isNativeAvailable) {
            Class[] classArray16 = new Class[]{MappedClasses.q0, Integer.TYPE};
            Class clazz16 = MappedClasses.lf;
            boolean bl16 = false;
            String string16 = "offset";
            MBlockPos mBlockPos16 = this;
            this.a = this.Y(string16, bl16, clazz16, classArray16);
        } else if (ForgeVersion.MC_1_17.d()) {
            Class[] classArray17 = new Class[]{MappedClasses.q0, Integer.TYPE};
            Class clazz17 = MappedClasses.lf;
            boolean bl17 = false;
            String string17 = "m_5484_";
            MBlockPos mBlockPos17 = this;
            this.a = this.Y(string17, bl17, clazz17, classArray17);
        } else {
            Class[] classArray18 = new Class[]{MappedClasses.q0, Integer.TYPE};
            Class clazz18 = MappedClasses.lf;
            boolean bl18 = true;
            String string18 = "offset";
            MBlockPos mBlockPos18 = this;
            this.a = this.Y(string18, bl18, clazz18, classArray18);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            BlockData.y(new String[2]);
        }
    }

    public Object q(int n, int n2, int n3) {
        return this.E.newInstance(n, n2, n3);
    }

    private Object o(Object object) {
        return this.O.invokeObject(object, new Object[0]);
    }

    public static Object O(MBlockPos mBlockPos, Object object) {
        return mBlockPos.V(object);
    }

    private Object S(Object object) {
        return this.x.invokeObject(object, new Object[0]);
    }

    private Object P(Object object) {
        return this.Q.invokeObject(object, new Object[0]);
    }

    public static Object B(MBlockPos mBlockPos, Object object) {
        return mBlockPos.P(object);
    }
}

