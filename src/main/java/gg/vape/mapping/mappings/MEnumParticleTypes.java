package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEnumParticleTypes
extends Mapping {
    private MappingField w;
    private MappingMethod p;

    private String z(Object object) {
        return (String)this.p.invokeObject(object, new Object[0]);
    }

    private String q(Object object) {
        return (String)this.w.getObject(object);
    }

    public MEnumParticleTypes() {
        this(BlockData.W());
    }

    private MEnumParticleTypes(String[] stringArray) {
        super(MappedClasses.qi);
        if (stringArray != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_20_6.v()) {
                    Class[] classArray = new Class[]{};
                    Class<String> clazz = String.class;
                    boolean bl = true;
                    String string = "getParameters";
                    Class clazz2 = MappedClasses.j;
                    MEnumParticleTypes mEnumParticleTypes = this;
                    this.p = mEnumParticleTypes.registerInstanceMethodForOwner(clazz2, string, bl, clazz, classArray);
                }
            } else {
                Class<String> clazz = String.class;
                boolean bl = true;
                String string = "particleName";
                MEnumParticleTypes mEnumParticleTypes = this;
                this.w = mEnumParticleTypes.J(string, bl, clazz);
            }
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[]{};
            Class<String> clazz = String.class;
            boolean bl = true;
            String string = "getParameters";
            Class clazz3 = MappedClasses.j;
            MEnumParticleTypes mEnumParticleTypes = this;
            this.p = mEnumParticleTypes.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
            Class<String> clazz4 = String.class;
            boolean bl2 = true;
            String string2 = "particleName";
            MEnumParticleTypes mEnumParticleTypes2 = this;
            this.w = this.J(string2, bl2, clazz4);
        }
    }


    public static String i(MEnumParticleTypes mEnumParticleTypes, Object object) {
        return mEnumParticleTypes.z(object);
    }

    public static String I(MEnumParticleTypes mEnumParticleTypes, Object object) {
        return mEnumParticleTypes.q(object);
    }
}

