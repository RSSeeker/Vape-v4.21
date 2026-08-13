package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Optional;

public class MHolder
extends Mapping {
    private MappingMethod j;
    private MappingMethod K;
    private MappingMethod M;
    private MappingMethod C;
    private MappingMethod S;

    public static Object m(MHolder mHolder, Object object) {
        return mHolder.o(object);
    }

    public MHolder() {
        this(BlockData.W());
    }

    private MHolder(String[] stringArray) {
        super(MappedClasses.Vo);
        Class[] classArray = new Class[]{};
        Class<Object> clazz = Object.class;
        boolean bl = true;
        String string = "value";
        MHolder mHolder = this;
        this.K = this.Y(string, bl, clazz, classArray);
        if (stringArray != null) {
            Class[] classArray2 = new Class[]{};
            Class<Optional> clazz2 = Optional.class;
            boolean bl2 = true;
            String string2 = "unwrapKey";
            MHolder mHolder2 = this;
            this.C = this.Y(string2, bl2, clazz2, classArray2);
            Class[] classArray3 = new Class[]{MappedClasses.qB};
            Class<Boolean> clazz3 = Boolean.TYPE;
            boolean bl3 = true;
            String string3 = "is";
            MHolder mHolder3 = this;
            this.j = this.Y(string3, bl3, clazz3, classArray3);
            Class[] classArray4 = new Class[]{Object.class};
            Class clazz4 = MappedClasses.Vo;
            boolean bl4 = true;
            String string4 = "direct";
            MHolder mHolder4 = this;
            this.M = this.registerStaticMethod(string4, bl4, clazz4, classArray4);
            if (ForgeVersion.MC_1_21_0.d()) {
                Class[] classArray5 = new Class[]{};
                Class<String> clazz5 = String.class;
                boolean bl5 = true;
                String string5 = "getRegisteredName";
                MHolder mHolder5 = this;
                this.S = this.Y(string5, bl5, clazz5, classArray5);
            }
            return;
        }
        Class[] classArray6 = new Class[]{};
        Class<Optional> clazz6 = Optional.class;
        boolean bl6 = true;
        String string6 = "unwrapKey";
        MHolder mHolder6 = this;
        this.C = this.Y(string6, bl6, clazz6, classArray6);
        Class[] classArray7 = new Class[]{MappedClasses.qB};
        Class<Boolean> clazz7 = Boolean.TYPE;
        boolean bl7 = true;
        String string7 = "is";
        MHolder mHolder7 = this;
        this.j = this.Y(string7, bl7, clazz7, classArray7);
        Class[] classArray8 = new Class[]{Object.class};
        Class clazz8 = MappedClasses.Vo;
        boolean bl8 = true;
        String string8 = "direct";
        MHolder mHolder8 = this;
        this.S = this.registerStaticMethod(string8, bl8, clazz8, classArray8);
    }

    private Object o(Object object) {
        return this.C.invokeObject(object, new Object[0]);
    }

    public static String F(MHolder mHolder, Object object) {
        return mHolder.F(object);
    }

    private boolean B(Object object, Object object2) {
        return this.j.invokeBoolean(object, object2);
    }


    private Object f(Object object) {
        return this.K.invokeObject(object, new Object[0]);
    }

    private String F(Object object) {
        return (String)this.S.invokeObject(object, new Object[0]);
    }

    public static boolean q(MHolder mHolder, Object object, Object object2) {
        return mHolder.B(object, object2);
    }

    public static Object E(MHolder mHolder, Object object) {
        return mHolder.f(object);
    }

    private Object n(Object object) {
        return this.M.invokeObject(null, object);
    }

    public static Object f(MHolder mHolder, Object object) {
        return mHolder.n(object);
    }
}

