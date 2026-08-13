package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEnchantment;
import gg.vape.utils.EnchantmentUtil;
import gg.vape.wrapper.Wrapper;

import java.util.ArrayList;

public class Enchantment
extends Wrapper {
    public static Enchantment projectileProtection() {
        return Enchantment.getEnchantmentById(4);
    }

    public static Enchantment flame() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 25 : 50);
    }

    public static Enchantment baneOfArthropods() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 14 : 18);
    }

    private static boolean N() {
        return ForgeVersion.MC_1_16_5.d();
    }

    public static Enchantment smite() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 13 : 17);
    }

    public static Enchantment fireProtection() {
        return Enchantment.getEnchantmentById(1);
    }

    public static Enchantment fortune() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 22 : 35);
    }

    public static Enchantment getEnchantmentById(int n) {
        if (ForgeVersion.MC_1_12_2.d() && ForgeVersion.MC_1_16_5.v()) {
            return new Enchantment(MEnchantment.k(Enchantment.vapeInstance.getMappings().RC, n));
        }
        for (Enchantment enchantment : EnchantmentUtil.Y) {
            if (enchantment == null || enchantment.isNull() || enchantment.getId() != n) continue;
            return enchantment;
        }
        return new Enchantment(null);
    }

    public int getId() {
        if (this.isNull()) {
            return -1;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            Enchantment[] enchantmentArray = EnchantmentUtil.Y;
            for (int i = 0; i < enchantmentArray.length; ++i) {
                Enchantment enchantment = enchantmentArray[i];
                if (enchantment.getObject() != this.I) continue;
                return i;
            }
            return -1;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            return MEnchantment.m(Enchantment.vapeInstance.getMappings().RC, this.I);
        }
        return MEnchantment.o(Enchantment.vapeInstance.getMappings().RC, this.I);
    }

    public static Enchantment[] getEnchantments() {
        if (ForgeVersion.MC_1_21_4.d()) {
            return EnchantmentUtil.B();
        }
        if (ForgeVersion.MC_1_21_0.d()) {
            return new Enchantment[0];
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            Iterable iterable = (Iterable)MEnchantment.N(Enchantment.vapeInstance.getMappings().RC);
            ArrayList<Enchantment> arrayList = new ArrayList<Enchantment>();
            for (Object t : iterable) {
                Enchantment enchantment = new Enchantment(t);
                arrayList.add(enchantment);
            }
            return arrayList.toArray(new Enchantment[arrayList.size()]);
        }
        Object[] objectArray = MEnchantment.x(Enchantment.vapeInstance.getMappings().RC);
        if (objectArray == null) {
            return new Enchantment[0];
        }
        Enchantment[] enchantmentArray = new Enchantment[objectArray.length];
        for (int i = 0; i < objectArray.length; ++i) {
            enchantmentArray[i] = new Enchantment(objectArray[i]);
        }
        return enchantmentArray;
    }

    public static Enchantment looting() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 17 : 21);
    }

    public static Enchantment knockback() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 15 : 19);
    }

    public static Enchantment sweepingEdge() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 18 : 22);
    }

    public static Enchantment h() {
        if (!ForgeVersion.MC_1_21_0.d()) {
            return new Enchantment(null);
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            Enchantment enchantment = EnchantmentUtil.h("wind_burst");
            return enchantment != null ? enchantment : new Enchantment(null);
        }
        return null;
    }

    public static Enchantment unbreaking() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 21 : 34);
    }

    public static Enchantment thorns() {
        return Enchantment.getEnchantmentById(7);
    }

    public static Enchantment silkTouch() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 20 : 33);
    }

    public Enchantment(Object object) {
        super(object);
    }

    public static Enchantment protection() {
        return Enchantment.getEnchantmentById(0);
    }

    public static Enchantment a() {
        if (!ForgeVersion.MC_1_21_0.d()) {
            return new Enchantment(null);
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            Enchantment enchantment = EnchantmentUtil.h("breach");
            return enchantment != null ? enchantment : new Enchantment(null);
        }
        return null;
    }

    public static Enchantment i() {
        if (!ForgeVersion.MC_1_21_0.d()) {
            return new Enchantment(null);
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            Enchantment enchantment = EnchantmentUtil.h("density");
            return enchantment != null ? enchantment : new Enchantment(null);
        }
        return null;
    }

    public static Enchantment sharpness() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 12 : 16);
    }

    public static Enchantment power() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 23 : 48);
    }

    public static Enchantment efficiency() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 19 : 32);
    }

    public static Enchantment featherFalling() {
        return Enchantment.getEnchantmentById(2);
    }

    public String getTranslatedName(int n) {
        if (this.isNull()) {
            return "";
        }
        if (ForgeVersion.MC_1_21_0.d()) {
            Holder holder = Holder.A(this.getObject());
            ITextComponent iTextComponent = new ITextComponent(MEnchantment.O(Enchantment.vapeInstance.getMappings().RC, holder.getObject(), n));
            return iTextComponent.getFormattedText();
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            ITextComponent iTextComponent = new ITextComponent(MEnchantment.J(Enchantment.vapeInstance.getMappings().RC, this.I, n));
            return iTextComponent.getFormattedText();
        }
        return MEnchantment.E(Enchantment.vapeInstance.getMappings().RC, this.I, n);
    }

    public static Enchantment punch() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 24 : 49);
    }

    public static Enchantment fireAspect() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 16 : 20);
    }

    public static Enchantment mending() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 36 : 70);
    }


    public static Enchantment blastProtection() {
        return Enchantment.getEnchantmentById(3);
    }

    public static Enchantment infinity() {
        return Enchantment.getEnchantmentById(Enchantment.N() ? 26 : 51);
    }
}

