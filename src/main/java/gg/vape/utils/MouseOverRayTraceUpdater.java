package gg.vape.utils;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.blatant.HitBoxes;
import gg.vape.module.combat.Reach;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockRayTraceResult;
import gg.vape.wrapper.impl.Direction;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityRayTraceResult;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PlayerControllerMP;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.WorldClient;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

public class MouseOverRayTraceUpdater {
    private static HitBoxes s;
    private static Reach t;

    public static void s(float f, float f2) {
        if (SharedModuleControlClaims.mouseOverUpdate.isClaimed()) {
            return;
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            MouseOverRayTraceUpdater.p(f, f2);
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            MouseOverRayTraceUpdater.M(f, f2);
            return;
        }
        MouseOverRayTraceUpdater.j(f, f2);
    }

    private static void M(float f, float f2) {
        float f3 = 1.0f;
        EntityLivingBase entityLivingBase = Minecraft.F();
        PlayerControllerMP playerControllerMP = Minecraft.playerController();
        if (entityLivingBase.isNotNull()) {
            Entity entity = new Entity(null);
            Minecraft.W(entity);
            double d = playerControllerMP.N();
            Minecraft.O(entityLivingBase.W(d, f3));
            Vec3 vec3 = entityLivingBase.k(f3);
            double d2 = d;
            boolean bl = false;
            if (d > 3.0) {
                bl = true;
            }
            d2 = f;
            d = f;
            d2 *= d2;
            if (Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0().isNotNull()) {
                double d3;
                d = d3 = (double)playerControllerMP.N() + ((double)f - 3.0);
                d2 = entityLivingBase.W(d3, 1.0f).getHitVec().A(vec3);
                if (ForgeVersion.MC_1_17.d()) {
                    d2 *= d2;
                }
            }
            Vec3 vec32 = entityLivingBase.J(1.0f);
            Vec3 vec33 = vec3.addVector(vec32.getX() * d, vec32.getY() * d, vec32.getZ() * d);
            double d4 = 1.0;
            AxisAlignedBB axisAlignedBB = entityLivingBase.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().c(vec32.r(d)).z(d4, d4, d4);
            EntityRayTraceResult entityRayTraceResult = MouseOverRayTraceUpdater.o(entityLivingBase, vec3, vec33, axisAlignedBB, d2, f2, null);
            if (entityRayTraceResult.isNotNull()) {
                Entity entity2 = entityRayTraceResult.getHitEntity();
                Vec3 vec34 = entityRayTraceResult.getHitVec();
                double d5 = vec3.H(vec34);
                if (bl && d5 > (double)(f * f)) {
                    Minecraft.O(BlockRayTraceResult.createMiss(vec34, Direction.i(vec32.getX(), vec32.getY(), vec32.getZ()), BlockPos.P(vec34)));
                } else if (d5 < d2 || Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0().isNull()) {
                    Minecraft.O(entityRayTraceResult);
                    if (entity2.isInstance(MappedClasses.zm) || entity2.isInstance(MappedClasses.lL)) {
                        Minecraft.W(entity2);
                    }
                }
            }
        }
    }

    private static void j(double d, float f) {
        float f2 = 1.0f;
        EntityLivingBase entityLivingBase = Minecraft.F();
        WorldClient worldClient = Minecraft.theWorld();
        PlayerControllerMP playerControllerMP = Minecraft.playerController();
        if (entityLivingBase.isNotNull() && worldClient.isNotNull()) {
            Wrapper wrapper;
            Entity entity = new Entity(null);
            Minecraft.W(entity);
            double d2 = playerControllerMP.N();
            RayTraceResult rayTraceResult = entityLivingBase.W(d2, f2);
            Minecraft.O(rayTraceResult);
            double d3 = d;
            Vec3 vec3 = entityLivingBase.O(f2);
            boolean bl = false;
            d3 = d;
            d2 = d;
            if (d2 > 3.0) {
                bl = true;
            }
            if (Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0().isNotNull()) {
                d3 = entityLivingBase.W((double)playerControllerMP.N() + (d - 3.0), f2).getHitVec().distanceTo(vec3);
            }
            if (d3 > 6.0) {
                d3 = 6.0;
            }
            Vec3 vec32 = entityLivingBase.J(f2);
            Vec3 vec33 = vec3.addVector(vec32.getX() * d2, vec32.getY() * d2, vec32.getZ() * d2);
            Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().setPointedEntity(entity);
            Vec3 vec34 = null;
            float f3 = 1.0f;
            List list = worldClient.F(entityLivingBase, entityLivingBase.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().addCoord(vec32.getX() * d2, vec32.getY() * d2, vec32.getZ() * d2).expand(f3, f3, f3));
            double d4 = d3;
            for (int i = 0; i < list.size(); ++i) {
                double d5;
                wrapper = new Entity(list.get(i));
                if (!((Entity)wrapper).n$src$Z$fx7gig()) continue;
                float f4 = ((Entity)wrapper).b() + f;
                AxisAlignedBB axisAlignedBB = ((Entity)wrapper).R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().expand(f4, f4, f4);
                RayTraceResult rayTraceResult2 = axisAlignedBB.calculateIntercept(vec3, vec33);
                if (axisAlignedBB.isVecInside(vec3)) {
                    if (!(0.0 < d4) && d4 != 0.0) continue;
                    Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().setPointedEntity((Entity)wrapper);
                    vec34 = rayTraceResult2.isNull() ? new Vec3(vec3) : rayTraceResult2.getHitVec();
                    d4 = 0.0;
                    continue;
                }
                if (!rayTraceResult2.isNotNull() || !((d5 = vec3.distanceTo(rayTraceResult2.getHitVec())) < d4) && d4 != 0.0) continue;
                if (wrapper.equals(entityLivingBase.S$src$Lgg_vape_wrapper_impl_Entity_$dgzs12()) && !((Entity)wrapper).C$src$Z$f9kazx()) {
                    if (d4 != 0.0) continue;
                    Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().setPointedEntity((Entity)wrapper);
                    vec34 = rayTraceResult2.getHitVec();
                    continue;
                }
                Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().setPointedEntity((Entity)wrapper);
                vec34 = rayTraceResult2.getHitVec();
                d4 = d5;
            }
            Entity entity2 = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().getPointedEntity();
            if (ForgeVersion.MC_1_7_10.Y() && entity2.isNotNull() && bl && vec3.distanceTo(vec34) > d) {
                Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().setPointedEntity(entity);
                wrapper = RayTraceResult.create(RayTraceResult_type.miss(), vec34, new EnumFacing(null), BlockPos.P(vec34));
                Minecraft.O((RayTraceResult)wrapper);
                entity2 = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().getPointedEntity();
            }
            if (entity2.isNotNull() && (d4 < d3 || Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0().isNull())) {
                wrapper = RayTraceResult.create(entity2, vec34);
                Minecraft.O((RayTraceResult)wrapper);
                if (entity2.isInstance(MappedClasses.zm) || entity2.isInstance(MappedClasses.lL)) {
                    Minecraft.W(entity2);
                }
            }
        }
    }

    static {
        t = null;
        s = null;
    }

    public static EntityRayTraceResult o(Entity entity, Vec3 vec3, Vec3 vec32, AxisAlignedBB axisAlignedBB, double d, double d2, @Nullable Predicate<Entity> predicate) {
        WorldClient worldClient = Minecraft.theWorld();
        double d3 = d;
        Entity entity2 = new Entity(null);
        Vec3 vec33 = new Vec3(null);
        List list = worldClient.F(entity, axisAlignedBB);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Vec3 vec34;
            double d4;
            Entity entity3 = new Entity(iterator.next());
            if (entity3.O$src$Z$fg5u49() || !entity3.c$src$Z$fr5pzh() || predicate != null && !predicate.test(entity3)) continue;
            AxisAlignedBB axisAlignedBB2 = entity3.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().I((double)entity3.b() + d2);
            Optional optional = axisAlignedBB2.b(vec3, vec32);
            if (axisAlignedBB2.E(vec3)) {
                entity2 = new Entity(entity3);
                vec33 = new Vec3(optional.orElse(vec3));
                d3 = 0.0;
                continue;
            }
            if (!optional.isPresent() || !((d4 = vec3.A(vec34 = new Vec3(optional.get()))) < d3) && d3 != 0.0) continue;
            if (entity3.q$src$Lgg_vape_wrapper_impl_Entity_$1ujnemc().equals(entity.q$src$Lgg_vape_wrapper_impl_Entity_$1ujnemc())) {
                if (d3 != 0.0) continue;
                entity2 = new Entity(entity3);
                vec33 = new Vec3(vec34);
                continue;
            }
            entity2 = new Entity(entity3);
            vec33 = new Vec3(vec34);
            d3 = d4;
        }
        if (entity2.isNull()) {
            return new EntityRayTraceResult(null);
        }
        return EntityRayTraceResult.create(entity2, vec33);
    }


    public static void b(boolean bl) {
        if (t == null) {
            t = Vape.INSTANCE.getModManager().getMod(Reach.class);
            s = Vape.INSTANCE.getModManager().getMod(HitBoxes.class);
        }
        if (Minecraft.thePlayer().isNull()) {
            return;
        }
        if (t.isEnabled() && !t.getMisplaceSetting().getEffectiveValue().booleanValue() || s.isEnabled()) {
            float f = (float)t.getReachDistance();
            float f2 = bl ? 0.0f : s.getExpansionAmount();
            MouseOverRayTraceUpdater.s(f, f2);
        }
    }

    private static void p(float f, float f2) {
        float f3 = 1.0f;
        EntityLivingBase entityLivingBase = Minecraft.F();
        PlayerControllerMP playerControllerMP = Minecraft.playerController();
        if (entityLivingBase.isNotNull()) {
            Entity entity = new Entity(null);
            Minecraft.W(entity);
            double d = playerControllerMP.N();
            Minecraft.O(entityLivingBase.W(d, f3));
            Vec3 vec3 = entityLivingBase.k(f3);
            double d2 = d;
            boolean bl = false;
            if (d > 3.0) {
                bl = true;
            }
            d2 = f;
            d = f;
            d2 *= d2;
            if (Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0().isNotNull()) {
                double d3 = (double)playerControllerMP.N() + ((double)f - 3.0);
                d2 = entityLivingBase.W(d3, 1.0f).getHitVec().A(vec3);
                d2 *= d2;
            }
            Vec3 vec32 = entityLivingBase.J(1.0f);
            Vec3 vec33 = vec3.addVector(vec32.getX() * d, vec32.getY() * d, vec32.getZ() * d);
            double d4 = 1.0;
            AxisAlignedBB axisAlignedBB = entityLivingBase.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().c(vec32.r(d)).z(d4, d4, d4);
            EntityRayTraceResult entityRayTraceResult = MouseOverRayTraceUpdater.o(entityLivingBase, vec3, vec33, axisAlignedBB, d2, f2, null);
            if (entityRayTraceResult.isNotNull()) {
                Entity entity2 = entityRayTraceResult.getHitEntity();
                Vec3 vec34 = entityRayTraceResult.getHitVec();
                double d5 = vec3.H(vec34);
                if (bl && d5 > (double)(f * f)) {
                    Minecraft.O(BlockRayTraceResult.createMiss(vec34, Direction.i(vec32.getX(), vec32.getY(), vec32.getZ()), BlockPos.P(vec34)));
                } else if (d5 < d2 || Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0().isNull()) {
                    Minecraft.O(entityRayTraceResult);
                    if (entity2.isInstance(MappedClasses.zm) || entity2.isInstance(MappedClasses.lL)) {
                        Minecraft.W(entity2);
                    }
                }
            }
        }
    }
}

