package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Chunk
extends Wrapper {
    private Biome x(BlockPos blockPos, ChunkWorldBridge chunkWorldBridge) {
        return new Biome(Chunk.vapeInstance.getMappings().qD.N(this.I, blockPos.getObject(), chunkWorldBridge.getObject()));
    }

    public int q(int n) {
        if (ForgeVersion.MC_1_20_6.d()) {
            return Chunk.vapeInstance.getMappings().qD.I(this.I, n);
        }
        return n >> 4;
    }

    public int j() {
        return Chunk.vapeInstance.getMappings().qD.A(this.I);
    }

    public int a() {
        return Chunk.vapeInstance.getMappings().qD.M(this.I);
    }

    private static boolean lambda$getEntitiesWithinAABBForEntity$0(Object object) {
        return true;
    }

    public boolean F() {
        return Chunk.vapeInstance.getMappings().qD.c(this.I);
    }

    private static void lambda$getEntitiesWithinAABBForEntity$1(AxisAlignedBB axisAlignedBB, Predicate predicate, List list, Object object) {
        if (object != axisAlignedBB && predicate.test(object)) {
            list.add(new Entity(object));
        }
    }

    public Chunk(Object object) {
        super(object);
    }


    public Map l() {
        return (Map)Chunk.vapeInstance.getMappings().qD.j(this.I);
    }

    public List<ChunkSection> U() {
        Object[] objectArray = Chunk.vapeInstance.getMappings().qD.D(this.I);
        ArrayList<ChunkSection> arrayList = new ArrayList<ChunkSection>();
        for (Object object : objectArray) {
            arrayList.add(new ChunkSection(object));
        }
        return arrayList;
    }

    public Biome J(int n, int n2, ChunkWorldBridge chunkWorldBridge) {
        if (ForgeVersion.MC_1_16_5.d()) {
            return Minecraft.theWorld().Y(BlockPos.create(n, 0, n2));
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            return this.x(BlockPos.create(n, 0, n2), chunkWorldBridge);
        }
        return new Biome(Chunk.vapeInstance.getMappings().qD.D(this.I, n & 0xF, n2 & 0xF, chunkWorldBridge.getObject()));
    }

    public Object[] R() {
        return Chunk.vapeInstance.getMappings().qD.D(this.I);
    }

    public void d(Entity entity, AxisAlignedBB axisAlignedBB, List list, Object object) {
        if (ForgeVersion.MC_1_17.d()) {
            Predicate predicate = object == null ? Chunk::lambda$getEntitiesWithinAABBForEntity$0 : (Predicate)object;
            Minecraft.theWorld().getEntityGetter().forEachEntityInBounds(axisAlignedBB, entityObject -> Chunk.lambda$getEntitiesWithinAABBForEntity$1(axisAlignedBB, predicate, list, entityObject));
            return;
        }
        Chunk.vapeInstance.getMappings().qD.j(this.I, entity.getObject(), axisAlignedBB.getObject(), list, object);
    }
}

