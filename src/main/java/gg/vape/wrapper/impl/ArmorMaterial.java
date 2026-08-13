package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MArmorMaterial;
import gg.vape.wrapper.Wrapper;

public class ArmorMaterial
extends Wrapper {
    public static ArmorMaterial gold() {
        return new ArmorMaterial(MArmorMaterial.getGold(ArmorMaterial.vapeInstance.getMappingsMapperCompat().e));
    }

    public static ArmorMaterial iron() {
        return new ArmorMaterial(MArmorMaterial.getIron(ArmorMaterial.vapeInstance.getMappingsMapperCompat().e));
    }

    public static ArmorMaterial chain() {
        return new ArmorMaterial(MArmorMaterial.getChain(ArmorMaterial.vapeInstance.getMappingsMapperCompat().e));
    }

    public static ArmorMaterial leather() {
        return new ArmorMaterial(MArmorMaterial.getLeather(ArmorMaterial.vapeInstance.getMappingsMapperCompat().e));
    }

    public ArmorMaterial(Object wrappedObject) {
        super(wrappedObject);
    }

    public static ArmorMaterial diamond() {
        return new ArmorMaterial(MArmorMaterial.getDiamond(ArmorMaterial.vapeInstance.getMappingsMapperCompat().e));
    }
}
