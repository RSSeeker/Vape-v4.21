package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MItemStack;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MItemPotion
extends Mapping {
    private final MappingMethod f;
    private MappingMethod G;

    public static Object M(MItemPotion mItemPotion, Object object, Object object2) {
        return mItemPotion.V(object, object2);
    }

    private List M(Object object, Object object2) {
        return (List)this.G.invokeObject(object, object2);
    }

    public MItemPotion() {
        this(MItemStack.f());
    }

    private MItemPotion(int n) {
        super(MappedClasses.Di);
        int n2 = n;
        if (ForgeVersion.MC_1_12_2.d()) {
            if (ForgeVersion.MC_1_20_6.v()) {
                Class[] classArray = new Class[]{MappedClasses.VK};
                Class<List> clazz = List.class;
                boolean bl = true;
                String string = "getEffectsFromStack";
                Class clazz2 = MappedClasses.uV;
                MItemPotion mItemPotion = this;
                this.G = mItemPotion.registerStaticMethodForOwner(clazz2, string, bl, clazz, classArray);
            }
        } else {
            Class[] classArray = new Class[]{MappedClasses.VK};
            Class<List> clazz = List.class;
            boolean bl = true;
            String string = "getEffects";
            MItemPotion mItemPotion = this;
            this.G = mItemPotion.Y(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray = new Class[]{MappedClasses.VK};
            Class clazz = MappedClasses.Yr;
            boolean bl = true;
            String string = "getDisplayName";
            Class clazz3 = MappedClasses.lb;
            MItemPotion mItemPotion = this;
            this.f = mItemPotion.registerInstanceMethodForOwner(clazz3, string, bl, clazz, classArray);
        } else {
            Class[] classArray = new Class[]{MappedClasses.VK};
            Class<String> clazz = String.class;
            boolean bl = true;
            String string = "getItemStackDisplayName";
            MItemPotion mItemPotion = this;
            this.f = mItemPotion.Y(string, bl, clazz, classArray); 
        }
    }

    public static List R(MItemPotion mItemPotion, Object object, Object object2) {
        return mItemPotion.M(object, object2);
    }

    private Object V(Object object, Object object2) {
        return this.f.invokeObject(object, object2);
    }

    public static String B(MItemPotion mItemPotion, Object object, Object object2) {
        return mItemPotion.W(object, object2);
    }


    private String W(Object object, Object object2) {
        return (String)this.f.invokeObject(object, object2);
    }
}
