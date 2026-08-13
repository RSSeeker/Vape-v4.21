package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class Material
extends Wrapper {
    private static Material air;
    private static Material water;
    private static Material vine;

    public boolean isReplaceable() {
        return Material.vapeInstance.getMappingsMapperCompat().Cn.isReplaceable(this.I);
    }


    public static Material vine() {
        if (vine == null) {
            vine = new Material(Material.vapeInstance.getMappingsMapperCompat().Cn.getVine());
        }
        return vine;
    }

    public boolean isToolNotRequired() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return false;
        }
        return Material.vapeInstance.getMappingsMapperCompat().Cn.isToolNotRequired(this.I);
    }

    public static Material air() {
        if (air == null) {
            air = new Material(Material.vapeInstance.getMappingsMapperCompat().Cn.getAir());
        }
        return air;
    }

    public static Material water() {
        if (water == null) {
            water = new Material(Material.vapeInstance.getMappingsMapperCompat().Cn.getWater());
        }
        return water;
    }

    public static Material fire() {
        return new Material(Material.vapeInstance.getMappingsMapperCompat().Cn.getFire());
    }

    public boolean isLiquid() {
        return Material.vapeInstance.getMappingsMapperCompat().Cn.isLiquid(this.I);
    }

    public Material(Object object) {
        super(object);
    }

    public boolean blocksMovement() {
        return Material.vapeInstance.getMappingsMapperCompat().Cn.blocksMovement(this.I);
    }

    public boolean isSolid() {
        return Material.vapeInstance.getMappingsMapperCompat().Cn.isSolid(this.I);
    }
}

