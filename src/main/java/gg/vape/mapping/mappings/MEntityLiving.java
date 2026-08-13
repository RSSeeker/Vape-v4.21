package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntity;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Map;

public class MEntityLiving
extends Mapping {
    private MappingField A;
    private final MappingMethod d;
    private MappingMethod w;
    private MappingField x;

    public static boolean S(MEntityLiving mEntityLiving, Object object) {
        return mEntityLiving.I(object);
    }

    public static Map l(MEntityLiving mEntityLiving, Object object) {
        return mEntityLiving.t(object);
    }

    private Object P(Object object, Object object2) {
        return this.d.invokeObject(object, object2);
    }

    public static Object v(MEntityLiving mEntityLiving, Object object, int n) {
        return mEntityLiving.K(object, n);
    }

    private Map t(Object object) {
        return (Map)this.A.getObject(object);
    }

    private Object K(Object object, int n) {
        return this.d.invokeObject(object, n);
    }

    private void o(Object object, boolean bl) {
        this.x.setBoolean(object, bl);
    }


    public static Object L(MEntityLiving mEntityLiving, Object object, Object object2) {
        return mEntityLiving.P(object, object2);
    }

    private boolean I(Object object) {
        return this.x.getBoolean(object);
    }

    public static Object F(MEntityLiving mEntityLiving, Object object) {
        return mEntityLiving.w(object);
    }

    public MEntityLiving() {
        this(MEntity.P());
    }

    private MEntityLiving(int n) {
        super(MappedClasses.zQ);
        if (n != 0) {
            Class[] classArray = new Class[]{Integer.TYPE};
            Class clazz = MappedClasses.VK;
            boolean bl = true;
            String string = "getEquipmentInSlot";
            MEntityLiving mEntityLiving = this;
            this.d = mEntityLiving.Y(string, bl, clazz, classArray);
            if (ForgeVersion.MC_1_8_9.Y()) {
                Class[] classArray2 = new Class[]{};
                Class<Iterable> clazz2 = Iterable.class;
                boolean bl2 = true;
                String string2 = "getArmorInventoryList";
                MEntityLiving mEntityLiving2 = this;
                this.w = this.Y(string2, bl2, clazz2, classArray2);
            }
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            Class<Map> clazz = Map.class;
            boolean bl = true;
            String string = "activePotionsMap";
            MEntityLiving mEntityLiving = this;
            this.A = mEntityLiving.J(string, bl, clazz);
            Class<Boolean> clazz3 = Boolean.TYPE;
            boolean bl3 = true;
            String string3 = "potionsNeedUpdate";
            MEntityLiving mEntityLiving3 = this;
            this.x = this.J(string3, bl3, clazz3);
            Class[] classArray = new Class[]{MappedClasses.um};
            Class clazz4 = MappedClasses.VK;
            boolean bl4 = true;
            String string4 = "getItemStackFromSlot";
            MEntityLiving mEntityLiving4 = this;
            this.d = this.Y(string4, bl4, clazz4, classArray);
        } else {
            Class[] classArray = new Class[]{Integer.TYPE};
            Class clazz = MappedClasses.VK;
            boolean bl = true;
            String string = "getEquipmentInSlot";
            MEntityLiving mEntityLiving = this;
            this.d = mEntityLiving.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_8_9.Y() && ForgeVersion.MC_1_21_6.v()) {
            Class[] classArray = new Class[]{};
            Class<Iterable> clazz = Iterable.class;
            boolean bl = true;
            String string = "getArmorInventoryList";
            MEntityLiving mEntityLiving = this;
            this.w = mEntityLiving.Y(string, bl, clazz, classArray); 
        }
    }

    private Object w(Object object) {
        return this.w.invokeObject(object, new Object[0]);
    }

    public static void b(MEntityLiving mEntityLiving, Object object, boolean bl) {
        mEntityLiving.o(object, bl);
    }
}

