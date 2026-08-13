package gg.vape.wrapper.impl;

import java.util.List;

public class WorldClient
extends World {
    private static String o;

    public void D(int n, Entity entity) {
        WorldClient.vapeInstance.getMappingsCompat().ha.Z(this.I, n, entity);
    }

    public List S() {
        return super.z();
    }

    public ClientChunkProvider U() {
        return new ClientChunkProvider(WorldClient.vapeInstance.getMappingsCompat().ha.y(this.I));
    }

    public static void J(String string) {
        o = string;
    }

    @Override
    public Entity V(int n) {
        return new Entity(WorldClient.vapeInstance.getMappingsCompat().ha.t(this.I, n));
    }

    public ClientWorldInfo getWorldInfo() {
        return new ClientWorldInfo(WorldClient.vapeInstance.getMappingsCompat().ha.u(this.I));
    }

    public static String b() {
        return o;
    }

    public WorldClient(Object object) {
        super(object);
    }

    static {
        if (WorldClient.b() == null) {
            WorldClient.J("hSIdQb");
        }
    }
}
