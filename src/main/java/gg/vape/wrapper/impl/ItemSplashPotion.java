package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MItemPotion;

import java.util.ArrayList;
import java.util.List;

public class ItemSplashPotion
extends Item {
    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        if (ForgeVersion.MC_1_16_5.d()) {
            ITextComponent iTextComponent = new ITextComponent(MItemPotion.M(ItemSplashPotion.vapeInstance.getMappings().CI, this.I, itemStack.getObject()));
            return iTextComponent.getFormattedText();
        }
        return MItemPotion.B(ItemSplashPotion.vapeInstance.getMappings().CI, this.I, itemStack.getObject());
    }

    public List getRawPotionEffects(ItemStack itemStack) {
        if (ForgeVersion.MC_1_20_6.d()) {
            BiomeProvider biomeProvider = new BiomeProvider(itemStack.w(DataComponents.T()));
            ArrayList arrayList = new ArrayList();
            for (Object t : biomeProvider.getAllEffects()) {
                arrayList.add(t);
            }
            return arrayList;
        }
        return MItemPotion.R(ItemSplashPotion.vapeInstance.getMappings().CI, this.I, itemStack.getObject());
    }

    public List<PotionEffect> getPotionEffects(ItemStack itemStack) {
        ArrayList<PotionEffect> arrayList = new ArrayList<PotionEffect>();
        List list = ForgeVersion.MC_1_20_6.d() ? this.getRawPotionEffects(itemStack) : MItemPotion.R(ItemSplashPotion.vapeInstance.getMappings().CI, this.I, itemStack.getObject());
        for (Object e : list) {
            arrayList.add(new PotionEffect(e));
        }
        return arrayList;
    }


    public static boolean isSplashPotion(ItemStack itemStack) {
        if (ForgeVersion.MC_1_12_2.d()) {
            boolean bl = itemStack.getItem().isNotNull() && itemStack.getItem().isInstance(MappedClasses.o);
            return bl;
        }
        boolean bl = (itemStack.L() & 0x4000) != 0;
        return bl;
    }

    public List b(ItemStack itemStack) {
        return MItemPotion.R(ItemSplashPotion.vapeInstance.getMappings().CI, this.I, itemStack.getObject());
    }

    public List<PotionEffect> getPotionEffects(int n) {
        ArrayList<PotionEffect> arrayList = new ArrayList<PotionEffect>();
        List list = MItemPotion.R(ItemSplashPotion.vapeInstance.getMappings().CI, this.I, n);
        for (Object e : list) {
            arrayList.add(new PotionEffect(e));
        }
        return arrayList;
    }

    public ItemSplashPotion(Object object) {
        super(object);
    }
}

