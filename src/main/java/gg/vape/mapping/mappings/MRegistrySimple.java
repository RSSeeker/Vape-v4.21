package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Set;

public class MRegistrySimple
extends Mapping {
    private final MappingMethod e;
    private final MappingMethod c;

    private Object f(Object object, Object object2) {
        return this.e.invokeObject(object, object2);
    }

    public Set g(Object object) {
        return (Set)this.c.invokeObject(object, new Object[0]);
    }


    public MRegistrySimple() {
        this(BlockData.W());
    }

    private MRegistrySimple(String[] stringArray) {
        super(MappedClasses.Vs);
        if (stringArray != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                Class[] classArray = new Class[]{};
                Class<Set> clazz = Set.class;
                boolean bl = true;
                String string = "keySet";
                Class clazz2 = MappedClasses.Fk;
                MRegistrySimple mRegistrySimple = this;
                this.c = mRegistrySimple.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
                Class[] classArray2 = new Class[]{MappedClasses.zC};
                Class<Object> clazz3 = Object.class;
                String string2 = "getOrDefault";
                MRegistrySimple mRegistrySimple2 = this;
                this.e = ((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string2, clazz3, classArray2).setNameForVersion(ForgeVersion.MC_1_21_4.n(), "getValue")).setNameForVersion(ForgeVersion.MC_1_17.n(), "get")).setOwnerClassForVersion(ForgeVersion.MC_1_21_4.n(), MappedClasses.Fk)).buildMethod();
            } else {
                Class[] classArray = new Class[]{};
                Class<Set> clazz = Set.class;
                boolean bl = true;
                String string = "getKeys";
                MRegistrySimple mRegistrySimple = this;
                this.c = mRegistrySimple.Y(string, bl, clazz, classArray);
                Class[] classArray3 = new Class[]{Object.class};
                Class<Object> clazz4 = Object.class;
                boolean bl2 = true;
                String string3 = "getObject";
                MRegistrySimple mRegistrySimple3 = this;
                this.e = this.Y(string3, bl2, clazz4, classArray3);
            }
            return;
        }
        Class[] classArray = new Class[]{Object.class};
        Class<Object> clazz = Object.class;
        boolean bl = true;
        String string = "getObject";
        MRegistrySimple mRegistrySimple = this;
        this.e = mRegistrySimple.Y(string, bl, clazz, classArray);
        this.c = null;
    }

    public static Object s(MRegistrySimple mRegistrySimple, Object object, Object object2) {
        return mRegistrySimple.f(object, object2);
    }
}

