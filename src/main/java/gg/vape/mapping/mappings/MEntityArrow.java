package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityArrow
extends Mapping {
    private MappingField p;
    private MappingField T;
    private MappingField l;
    private MappingField c;

    public MEntityArrow() {
        this(MSPacketMapChunkBulk.getMappingControlFlowToken());
    }

    private MEntityArrow(String string) {
        super(MappedClasses.qZ);
        if (string != null) {
            if (ForgeVersion.MC_1_21_0.d()) {
                Class<Double> clazz = Double.TYPE;
                boolean bl = true;
                String string2 = "accelerationPower";
                Class clazz2 = MappedClasses.ze;
                MEntityArrow mEntityArrow = this;
                this.l = mEntityArrow.registerInstanceFieldForOwner(clazz2, string2, bl, clazz);
            } else if (ForgeVersion.MC_1_16_5.d()) {
                Class<Double> clazz = Double.TYPE;
                boolean bl = Wrapper.isNativeAvailable;
                String string3 = "field_70232_b";
                Class clazz3 = MappedClasses.ze;
                MEntityArrow mEntityArrow = this;
                this.c = mEntityArrow.registerInstanceFieldForOwner(clazz3, string3, bl, clazz);
                Class<Double> clazz4 = Double.TYPE;
                boolean bl2 = Wrapper.isNativeAvailable;
                String string4 = "field_70233_c";
                Class clazz5 = MappedClasses.ze;
                MEntityArrow mEntityArrow2 = this;
                this.T = this.registerInstanceFieldForOwner(clazz5, string4, bl2, clazz4);
                Class<Double> clazz6 = Double.TYPE;
                boolean bl3 = Wrapper.isNativeAvailable;
                String string5 = "field_70230_d";
                Class clazz7 = MappedClasses.ze;
                MEntityArrow mEntityArrow3 = this;
                this.p = this.registerInstanceFieldForOwner(clazz7, string5, bl3, clazz6);
            } else {
                Class<Double> clazz = Double.TYPE;
                boolean bl = true;
                String string6 = "accelerationX";
                MEntityArrow mEntityArrow = this;
                this.c = mEntityArrow.J(string6, bl, clazz);
                Class<Double> clazz8 = Double.TYPE;
                boolean bl4 = true;
                String string7 = "accelerationY";
                MEntityArrow mEntityArrow4 = this;
                this.T = this.J(string7, bl4, clazz8);
                Class<Double> clazz9 = Double.TYPE;
                boolean bl5 = true;
                String string8 = "accelerationZ";
                MEntityArrow mEntityArrow5 = this;
                this.p = this.J(string8, bl5, clazz9);
            }
            return;
        }
        if (ForgeVersion.MC_1_21_0.d()) {
            Class<Double> clazz = Double.TYPE;
            boolean bl = Wrapper.isNativeAvailable;
            String string9 = "field_70232_b";
            Class clazz10 = MappedClasses.ze;
            MEntityArrow mEntityArrow = this;
            this.c = mEntityArrow.registerInstanceFieldForOwner(clazz10, string9, bl, clazz);
            Class<Double> clazz11 = Double.TYPE;
            boolean bl6 = Wrapper.isNativeAvailable;
            String string10 = "field_70233_c";
            Class clazz12 = MappedClasses.ze;
            MEntityArrow mEntityArrow6 = this;
            this.T = this.registerInstanceFieldForOwner(clazz12, string10, bl6, clazz11);
            Class<Double> clazz13 = Double.TYPE;
            boolean bl7 = Wrapper.isNativeAvailable;
            String string11 = "field_70230_d";
            Class clazz14 = MappedClasses.ze;
            MEntityArrow mEntityArrow7 = this;
            this.p = this.registerInstanceFieldForOwner(clazz14, string11, bl7, clazz13);
        }
        Class<Double> clazz = Double.TYPE;
        boolean bl = true;
        String string12 = "accelerationX";
        MEntityArrow mEntityArrow = this;
        this.c = mEntityArrow.J(string12, bl, clazz); 
        Class<Double> clazz15 = Double.TYPE;
        boolean bl8 = true;
        String string13 = "accelerationY";
        MEntityArrow mEntityArrow8 = this;
        this.T = this.J(string13, bl8, clazz15);
        Class<Double> clazz16 = Double.TYPE;
        boolean bl9 = true;
        String string14 = "accelerationZ";
        MEntityArrow mEntityArrow9 = this;
        this.p = this.J(string14, bl9, clazz16);
    }

    private double t(Object object) {
        return this.l.getDouble(object);
    }

    public static double d(MEntityArrow mEntityArrow, Object object) {
        return mEntityArrow.F(object);
    }

    public static double D(MEntityArrow mEntityArrow, Object object) {
        return mEntityArrow.h(object);
    }


    private double U(Object object) {
        return this.p.getDouble(object);
    }

    private double F(Object object) {
        return this.c.getDouble(object);
    }

    public static double t(MEntityArrow mEntityArrow, Object object) {
        return mEntityArrow.t(object);
    }

    public static double g(MEntityArrow mEntityArrow, Object object) {
        return mEntityArrow.U(object);
    }

    private double h(Object object) {
        return this.T.getDouble(object);
    }
}

