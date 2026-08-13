package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class GlStateManagerTexGenCoord
extends Wrapper {
    public GlStateManagerTexGenCoord(Object coordinateHandle) {
        super(coordinateHandle);
    }

    public static GlStateManagerTexGenCoord none() {
        return new GlStateManagerTexGenCoord(
                GlStateManagerTexGenCoord.vapeInstance.getMappingsMapperCompat().glTexGenCoord.getNone());
    }

    public static GlStateManagerTexGenCoord server() {
        return new GlStateManagerTexGenCoord(
                GlStateManagerTexGenCoord.vapeInstance.getMappingsMapperCompat().glTexGenCoord.getServer());
    }

    public static GlStateManagerTexGenCoord client() {
        return new GlStateManagerTexGenCoord(
                GlStateManagerTexGenCoord.vapeInstance.getMappingsMapperCompat().glTexGenCoord.getClient());
    }
}
