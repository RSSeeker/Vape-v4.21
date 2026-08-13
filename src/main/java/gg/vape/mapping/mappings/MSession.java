package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Optional;
import java.util.UUID;

public class MSession
extends Mapping {
    private final MappingMethod D;
    private final MappingField C;
    private final MappingField N;


    public MSession() {
        this(BlockData.W());
    }

    private MSession(String[] stringArray) {
        super(MappedClasses.qS);
        if (stringArray != null) {
            Class<String> clazz = String.class;
            boolean bl = true;
            String string = "playerID";
            MSession mSession = this;
            this.C = mSession.J(string, bl, clazz);
            Class<String> clazz2 = String.class;
            boolean bl2 = true;
            String string2 = "username";
            MSession mSession2 = this;
            this.N = this.J(string2, bl2, clazz2);
            if (ForgeVersion.MC_1_21_10.d()) {
                Class[] classArray = new Class[]{String.class, UUID.class, String.class, Optional.class, Optional.class};
                MSession mSession3 = this;
                this.D = this.registerConstructor(classArray);
            } else if (ForgeVersion.MC_1_20_6.d()) {
                Class[] classArray = new Class[]{String.class, UUID.class, String.class, Optional.class, Optional.class, MappedClasses.SESSION_TYPE};
                Class<Void> clazz3 = Void.TYPE;
                boolean bl3 = false;
                String string3 = "<init>";
                MSession mSession4 = this;
                this.D = this.Y(string3, bl3, clazz3, classArray);
            } else {
                Class[] classArray = new Class[]{String.class, String.class, String.class, String.class};
                Class<Void> clazz4 = Void.TYPE;
                boolean bl4 = false;
                String string4 = "<init>";
                MSession mSession5 = this;
                this.D = this.Y(string4, bl4, clazz4, classArray);
            }
            return;
        }
        Class<String> clazz = String.class;
        boolean bl = true;
        String string = "playerID";
        MSession mSession = this;
        this.C = mSession.J(string, bl, clazz);
        Class<String> clazz5 = String.class;
        boolean bl5 = true;
        String string5 = "username";
        MSession mSession6 = this;
        this.N = this.J(string5, bl5, clazz5);
        if (ForgeVersion.MC_1_21_10.d()) {
            Class[] classArray = new Class[]{String.class, UUID.class, String.class, Optional.class, Optional.class, MappedClasses.SESSION_TYPE};
            Class<Void> clazz6 = Void.TYPE;
            boolean bl6 = false;
            String string6 = "<init>";
            MSession mSession7 = this;
            mSession7.Y(string6, bl6, clazz6, classArray);
        }
        Class[] classArray = new Class[]{String.class, String.class, String.class, String.class};
        Class<Void> clazz7 = Void.TYPE;
        boolean bl7 = false;
        String string7 = "<init>";
        MSession mSession8 = this;
        this.D = this.Y(string7, bl7, clazz7, classArray);
    }

    public static Object t(MSession mSession, String string, String string2, String string3, String string4) {
        return mSession.t(string, string2, string3, string4);
    }

    public static Object B(MSession mSession, String string, UUID uUID, String string2, Optional optional, Optional optional2, Object object) {
        return mSession.O(string, uUID, string2, optional, optional2, object);
    }

    public String s(Object object) {
        return (String)this.N.getObject(object);
    }

    private Object t(String string, String string2, String string3, String string4) {
        return this.D.newInstance(string, string2, string3, string4);
    }

    public Object V(Object object) {
        return this.C.getObject(object);
    }

    private Object O(String string, UUID uUID, String string2, Optional<String> optional, Optional<String> optional2, Object object) {
        return this.D.newInstance(string, uUID, string2, optional, optional2, object);
    }
}

