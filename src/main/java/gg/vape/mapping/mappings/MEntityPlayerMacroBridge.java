package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityPlayerMacroBridge
extends Mapping {
    private final MappingField q;
    private final MappingField z;
    private MappingField g;

    private boolean s(Object object) {
        return this.z.getBoolean(object);
    }


    public static Object F(MEntityPlayerMacroBridge mEntityPlayerMacroBridge, Object object) {
        return mEntityPlayerMacroBridge.D(object);
    }

    private Object D(Object object) {
        return this.g.getObject(object);
    }

    public MEntityPlayerMacroBridge() {
        this(MSPacketMapChunkBulk.getMappingControlFlowToken());
    }

    private MEntityPlayerMacroBridge(String string) {
        super(MappedClasses.lM);
        if (string != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                Class clazz = MappedClasses.zc;
                boolean bl = true;
                String string2 = "caughtEntity";
                MEntityPlayerMacroBridge mEntityPlayerMacroBridge = this;
                this.q = mEntityPlayerMacroBridge.J(string2, bl, clazz);
                Class<Boolean> clazz2 = Boolean.TYPE;
                boolean bl2 = Wrapper.isNativeAvailable;
                String string3 = "field_234597_c_";
                MEntityPlayerMacroBridge mEntityPlayerMacroBridge2 = this;
                this.z = this.J(string3, bl2, clazz2);
            } else if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                Class clazz = MappedClasses.zc;
                boolean bl = true;
                String string4 = "caughtEntity";
                MEntityPlayerMacroBridge mEntityPlayerMacroBridge = this;
                this.q = mEntityPlayerMacroBridge.J(string4, bl, clazz);
                Class<Boolean> clazz3 = Boolean.TYPE;
                boolean bl3 = true;
                String string5 = "inGround";
                MEntityPlayerMacroBridge mEntityPlayerMacroBridge3 = this;
                this.z = this.J(string5, bl3, clazz3);
            } else {
                Class clazz = MappedClasses.zc;
                boolean bl = Wrapper.isNativeAvailable;
                String string6 = "field_146043_c";
                MEntityPlayerMacroBridge mEntityPlayerMacroBridge = this;
                this.q = mEntityPlayerMacroBridge.J(string6, bl, clazz);
                Class<Boolean> clazz4 = Boolean.TYPE;
                boolean bl4 = Wrapper.isNativeAvailable;
                String string7 = "field_146051_au";
                MEntityPlayerMacroBridge mEntityPlayerMacroBridge4 = this;
                this.z = this.J(string7, bl4, clazz4);
            }
            if (ForgeVersion.MC_1_16_5.v()) {
                Class clazz = MappedClasses.Yl;
                boolean bl = true;
                String string8 = "angler";
                MEntityPlayerMacroBridge mEntityPlayerMacroBridge = this;
                this.g = mEntityPlayerMacroBridge.J(string8, bl, clazz);
            }
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            Class clazz = MappedClasses.zc;
            boolean bl = true;
            String string9 = "caughtEntity";
            MEntityPlayerMacroBridge mEntityPlayerMacroBridge = this;
            mEntityPlayerMacroBridge.J(string9, bl, clazz);
            Class<Boolean> clazz5 = Boolean.TYPE;
            boolean bl5 = true;
            String string10 = "inGround";
            MEntityPlayerMacroBridge mEntityPlayerMacroBridge5 = this;
            mEntityPlayerMacroBridge5.J(string10, bl5, clazz5);
        }
        Class clazz = MappedClasses.zc;
        boolean bl = Wrapper.isNativeAvailable;
        String string11 = "field_146043_c";
        MEntityPlayerMacroBridge mEntityPlayerMacroBridge = this;
        this.q = mEntityPlayerMacroBridge.J(string11, bl, clazz); 
        Class<Boolean> clazz6 = Boolean.TYPE;
        boolean bl6 = Wrapper.isNativeAvailable;
        String string12 = "field_146051_au";
        MEntityPlayerMacroBridge mEntityPlayerMacroBridge6 = this;
        this.z = this.J(string12, bl6, clazz6);
        if (ForgeVersion.MC_1_16_5.v()) {
            Class clazz7 = MappedClasses.Yl;
            boolean bl7 = true;
            String string13 = "angler";
            MEntityPlayerMacroBridge mEntityPlayerMacroBridge7 = this;
            this.g = this.J(string13, bl7, clazz7);
        }
    }

    private Object k(Object object) {
        return this.q.getObject(object);
    }

    public static Object W(MEntityPlayerMacroBridge mEntityPlayerMacroBridge, Object object) {
        return mEntityPlayerMacroBridge.k(object);
    }

    public static boolean B(MEntityPlayerMacroBridge mEntityPlayerMacroBridge, Object object) {
        return mEntityPlayerMacroBridge.s(object);
    }
}

