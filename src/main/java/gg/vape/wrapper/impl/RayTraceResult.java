package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MRayTraceResult;
import gg.vape.wrapper.Wrapper;

public class RayTraceResult
extends Wrapper {
    private static int[] g;

    public int g() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return RayTraceResult.vapeInstance.getMappings().C0.a(this.I);
        }
        BlockPos blockPos = this.getBlockPos();
        return blockPos.getX();
    }

    public static RayTraceResult create(RayTraceResult_type rayTraceResult_type, Vec3 vec3, EnumFacing enumFacing, BlockPos blockPos) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Object object = RayTraceResult.vapeInstance.getMappings().C0.o(vec3.getObject(), enumFacing.getObject(), blockPos.getObject(), false);
            return new RayTraceResult(object);
        }
        return new RayTraceResult(RayTraceResult.vapeInstance.getMappings().C0.m(rayTraceResult_type.getObject(), vec3.getObject(), enumFacing.getObject(), blockPos.getObject()));
    }

    public RayTraceResult(Object object) {
        super(object);
    }

    public int Z() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return RayTraceResult.vapeInstance.getMappings().C0.n(this.I);
        }
        EnumFacing enumFacing = this.getSideHit();
        int n = enumFacing.isNotNull() ? enumFacing.Y() : 0;
        return n;
    }

    public Block Z$src$Lgg_vape_wrapper_impl_Block_$6x2c9a() {
        return Minecraft.theWorld().getBlockByPos(this.g(), this.T(), this.a$src$I$8nuo9d());
    }

    public int a$src$I$8nuo9d() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return RayTraceResult.vapeInstance.getMappings().C0.z(this.I);
        }
        BlockPos blockPos = this.getBlockPos();
        return blockPos.getZ();
    }

    public EnumFacing getSideHit() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return EnumFacing.T(RayTraceResult.vapeInstance.getMappings().C0.n(this.I));
        }
        return new EnumFacing(RayTraceResult.vapeInstance.getMappings().C0.P(this.I));
    }

    public Entity getEntity() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.g$src$Lgg_vape_wrapper_impl_Entity_$5c9han();
        }
        return new Entity(RayTraceResult.vapeInstance.getMappings().C0.F(this.I));
    }

    public static int[] O() {
        return g;
    }

    public static void B(int[] nArray) {
        g = nArray;
    }

    public RayTraceResult_type getTypeOfHit() {
        return new RayTraceResult_type(MRayTraceResult.Z(RayTraceResult.vapeInstance.getMappings().C0, this.I));
    }

    public Vec3 getHitVec() {
        return new Vec3(RayTraceResult.vapeInstance.getMappings().C0.o(this.I));
    }


    public boolean isEntityHit() {
        boolean bl = this.isNotNull() && this.getTypeOfHit().equals(RayTraceResult_type.entity());
        return bl;
    }

    private Entity g$src$Lgg_vape_wrapper_impl_Entity_$5c9han() {
        if (MappedClasses.zl.isInstance(this.I)) {
            return new Entity(RayTraceResult.vapeInstance.getMappings().C0.F(this.I));
        }
        return new Entity(null);
    }

    public boolean isBlockHit() {
        boolean bl = this.isNotNull() && this.getTypeOfHit().equals(RayTraceResult_type.block());
        return bl;
    }

    public void setEntity(Entity entity) {
        RayTraceResult.vapeInstance.getMappings().C0.a(this.I, entity.getObject());
    }

    public BlockPos getBlockPos() {
        if (ForgeVersion.MC_1_16_5.d()) {
            if (MappedClasses.qF.isInstance(this.I)) {
                return new BlockPos(RayTraceResult.vapeInstance.getMappings().C0.D(this.I));
            }
            return null;
        }
        return new BlockPos(RayTraceResult.vapeInstance.getMappings().C0.D(this.I));
    }

    public int T() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return RayTraceResult.vapeInstance.getMappings().C0.w(this.I);
        }
        BlockPos blockPos = this.getBlockPos();
        return blockPos.getY();
    }

    public static RayTraceResult create(Entity entity, Vec3 vec3) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Object object = RayTraceResult.vapeInstance.getMappings().C0.M(entity.getObject(), vec3.getObject());
            return new RayTraceResult(object);
        }
        return new RayTraceResult(RayTraceResult.vapeInstance.getMappings().C0.y(entity.getObject(), vec3.getObject()));
    }

    static {
        if (RayTraceResult.O() == null) {
            RayTraceResult.B(new int[2]);
        }
    }
}

